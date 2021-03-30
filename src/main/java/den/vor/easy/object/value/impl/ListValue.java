/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Value that encapsulates {@link List<Value>}.
 * Does not support any arithmetical or comparison operations.
 *
 * @param <T> type of value in each list entry
 */
public class ListValue<T> extends Value<List<T>> {

    private final List<Value<T>> values;
    private List<T> unwrappedValues;

    public ListValue(List<Value<T>> values) {
        this.values = values;
    }

    /**
     * Wraps a given list of elements into the list.
     * @param elements elements to wrap
     * @return list value instance
     */
    public static <T> ListValue<T> of(List<Value<T>> elements) {
        return new ListValue<>(elements);
    }

    /**
     * Wraps a given list of elements into the list.
     * @param elements elements to wrap
     * @return list value instance
     */
    public static <T> ListValue<T> of(Value<T>... elements) {
        return of(Arrays.asList(elements));
    }

    @Override
    public List<T> getValue() {
        if (unwrappedValues == null) {
            unwrappedValues = values.stream().map(Value::getValue).collect(Collectors.toList());
        }
        return unwrappedValues;
    }

    @Override
    public Value<?> get(ScalarValue<?> key) {
        if (key instanceof IntValue) {
            IntValue intValue = (IntValue) key;
            return values.get(intValue.getValue());
        }
        return super.get(key);
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return "ListValue{" + values + '}';
    }
}
