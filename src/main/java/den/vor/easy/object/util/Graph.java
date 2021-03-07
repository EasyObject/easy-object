/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Graph<T> {

    private final Map<T, ? extends Collection<T>> graph;
    private final Predicate<T> ignoreConnection;
    private List<T> order;

    public Graph(Map<T, ? extends Collection<T>> graph, Predicate<T> ignoreConnection) {
        this.graph = graph;
        this.ignoreConnection = ignoreConnection;
    }

    public Graph(Map<T, ? extends Collection<T>> graph) {
        this(graph, v -> false);
    }

    public List<T> getCreationOrder() {
        if (order != null) {
            return order;
        }
        order = calculateOrder();
        return order;
    }

    private List<T> calculateOrder() {
        List<Vertex<T>> vertices = buildVertexes(graph);
        List<T> order = new ArrayList<>();

        while (!vertices.isEmpty()) {
            List<Vertex<T>> resolvable = vertices.stream()
                    //TODO or remove instead of containsAll?
                    .filter(v -> order.containsAll(v.adjacent))
                    .collect(Collectors.toList());

            if (resolvable.isEmpty()) {
                //TODO change exception
                throw new RuntimeException("Resolved: " + order + ", not resolved: [" + vertices.stream()
                        .map(v -> "(" + v.key + " - " + v.adjacent + ")").collect(Collectors.joining(", ")) + "]");
            }

            order.addAll(resolvable.stream().map(v -> v.key).collect(Collectors.toList()));
            vertices.removeAll(resolvable);
        }

        return order;
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
