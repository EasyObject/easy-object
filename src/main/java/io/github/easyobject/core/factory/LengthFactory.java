/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.ref.FieldRef;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for all factories that generate values with variable length.
 *
 * @param <T> type of generated values
 * @param <R> corresponding wrapper type that extends {@link Value}
 */
public abstract class LengthFactory<T, R extends Value<T>> extends Factory<T, R> {

    /**
     * Factory that is used to determine the length of elements.
     */
    private ValueSource<Integer> lengthFactory;

    public LengthFactory(ValueSource<Integer> lengthFactory) {
        this.lengthFactory = lengthFactory;
    }

    public ValueSource<Integer> getLengthFactory() {
        return lengthFactory;
    }

    @Override
    public List<FieldRef> getDependencies() {
        List<FieldRef> dependencies = getNestedFactoriesDependencies().stream()
                .filter(s -> s.getParentLinks() != 0)
                .map(FieldRef::getReferenceForParentFactory)
                .collect(Collectors.toList());
        dependencies.addAll(getLengthFactory().getDependencies());
        return dependencies;
    }

    protected abstract List<FieldRef> getNestedFactoriesDependencies();
}
