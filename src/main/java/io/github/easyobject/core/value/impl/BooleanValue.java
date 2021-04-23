/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.ScalarValue;
import io.github.easyobject.core.value.Value;

import java.util.Objects;

import static io.github.easyobject.core.value.operator.impl.BooleanOperations.*;

/**
 * Value that encapsulates {@link Boolean}.
 * Does not support any arithmetical operations. Supports logical operations.
 */
public class BooleanValue extends ScalarValue<Boolean> {

    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    /**
     * Returns one of two constants depending on value provided.
     * @param value value to wrap
     */
    public static BooleanValue of(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "value=" + value +
                '}';
    }

    @Override
    public Value<?> and(Value<?> value) {
        return AND_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> or(Value<?> value) {
        return OR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> xor(Value<?> value) {
        return XOR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> not() {
        return of(!value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanValue that = (BooleanValue) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
