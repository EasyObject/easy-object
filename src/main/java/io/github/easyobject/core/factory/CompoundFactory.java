/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory;

import io.github.easyobject.core.ref.FieldRef;
import io.github.easyobject.core.util.Graph;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.ScalarValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Base class for factories that return values with several fields, generated by different factories.
 * @param <T> type of generated values
 * @param <R> corresponding wrapper type that extends {@link Value}
 */
public abstract class CompoundFactory<T, R extends Value<T>> extends Factory<T, R> {

    /**
     * Gets a map of child factories with corresponding field names.
     */
    protected abstract Map<? extends ScalarValue<?>, Factory<?, ?>> getChildFactories();

    @Override
    public List<FieldRef> getDependencies() {
        return getChildFactories().values().stream()
                .map(Factory::getDependencies)
                .flatMap(Collection::stream)
                .filter(ref -> ref.getParentLinks() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public Generator<R> getGenerator() {
        return doGetGenerator(getOrderedGenerators());
    }

    protected abstract Generator<R> doGetGenerator(List<ScalarValue<?>> orderedKeys);

    protected List<ScalarValue<?>> getOrderedGenerators() {
        Map<ScalarValue<?>, List<ScalarValue<?>>> childFactoriesDependencies = getChildFactories().entrySet().stream()
                .collect(toMap(Map.Entry::getKey, this::getFirstLinks));
        Graph<ScalarValue<?>> graph = new Graph<>(childFactoriesDependencies);
        return graph.getCreationOrder();
    }

    private List<ScalarValue<?>> getFirstLinks(Map.Entry<? extends ScalarValue<?>, Factory<?, ?>> e) {
        return e.getValue().getDependencies().stream()
                .filter(d -> d.getParentLinks() == 0)
                .map(FieldRef::getFirstPath)
                .collect(toList());
    }

    @Override
    protected void prepareInternal(PreparationContext preparationContext) {
        getChildFactories().values().forEach(f -> f.prepareInternal(preparationContext));
    }
}
