/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.util;

import io.github.easyobject.core.exception.impl.DependencyGraphNotResolvableException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a directed graph. Main purpose of this class is to calculate graph traversal order that follows the rules:
 * All nodes, that the next node points to, must be already visited.
 * Class is used to determine in which order object fields should be generated.
 * @param <T> type of vertex
 */
public class Graph<T> {

    /**
     * Map that represents a graph. Keys are vertexes, values - adjacent nodes.
     */
    private final Map<T, ? extends Collection<T>> nodes;
    /**
     * Predicate that allows to ignore some connections in a graph.
     * All edges are tested using the predicate and ignored, if it returns {@code true}.
     */
    private final Predicate<T> ignoreConnection;
    /**
     * Calculated vertex traversal order. It's cached on the first query to reduce total computations.
     */
    private List<T> order;

    public Graph(Map<T, ? extends Collection<T>> nodes, Predicate<T> ignoreConnection) {
        this.nodes = nodes;
        this.ignoreConnection = ignoreConnection;
    }

    public Graph(Map<T, ? extends Collection<T>> nodes) {
        this(nodes, v -> false);
    }

    public List<T> getCreationOrder() {
        if (order != null) {
            return order;
        }
        order = calculateOrder();
        return order;
    }

    /**
     * Calculates valid traversal order or throws an exception, if it doesn't exist.
     * Algorithm description:
     * Spins in the loop, on each iteration finds resolvable vertexes and removes them from the temporary collection.
     * If the temporary collection is empty, algorithm is finished.
     * If on any iteration can't find a resolvable vertex, throws an exception.
     * Naive algorithm complexity: {@code O(n^3)} where n is the number of vertexes.
     * @return traversal order
     * @throws DependencyGraphNotResolvableException if can't find a traversal order
     */
    private List<T> calculateOrder() {
        List<Vertex<T>> vertices = buildVertexes(nodes);
        List<T> vertexOrder = new ArrayList<>();

        while (!vertices.isEmpty()) {
            List<Vertex<T>> resolvable = vertices.stream()
                    //TODO or remove instead of containsAll?
                    .filter(v -> vertexOrder.containsAll(v.adjacent))
                    .collect(Collectors.toList());

            if (resolvable.isEmpty()) {
                List<T> notResolved = vertices.stream().map(v -> v.key).collect(Collectors.toList());
                throw new DependencyGraphNotResolvableException(vertexOrder, notResolved);
            }

            vertexOrder.addAll(resolvable.stream().map(v -> v.key).collect(Collectors.toList()));
            vertices.removeAll(resolvable);
        }

        return vertexOrder;
    }

    private List<Vertex<T>> buildVertexes(Map<T, ? extends Collection<T>> graph) {
        return graph.entrySet().stream()
                .map(e -> new Vertex<>(e.getKey(), getValue(e)))
                .sorted(Comparator.comparingInt(v -> v.adjacent.size()))
                .collect(Collectors.toList());
    }

    private Collection<T> getValue(Map.Entry<T, ? extends Collection<T>> e) {
        return e.getValue().stream()
                .filter(v -> !ignoreConnection.test(v))
                .collect(Collectors.toList());
    }

    private static final class Vertex<T> {

        private final T key;
        private final Collection<T> adjacent;

        private Vertex(T key, Collection<T> adjacent) {
            this.key = key;
            this.adjacent = adjacent;
        }
    }
}
