/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.Value;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Value that encapsulates {@link Set}.
 * Does not support any arithmetical or comparison operations.
 *
 * @param <T> type of value in each list entry
 */
public class SetValue<T> extends Value<Set<T>> {

    private final Set<Value<T>> values;
    private Set<T> unwrappedValues;

    public SetValue(Set<Value<T>> values) {
        this.values = values;
    }

    /**
     * Wraps a given set of elements into the set value.
     *
     * @param elements elements to wrap
     * @return set value instance
     */
    public static <T> SetValue<T> of(Set<Value<T>> elements) {
        return new SetValue<>(elements);
    }

    @Override
    public Set<T> getValue() {
        if (unwrappedValues == null) {
            unwrappedValues = values.stream().map(Value::getValue).collect(Collectors.toSet());
        }
        return unwrappedValues;
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return "SetValue{" + values + '}';
    }
}
