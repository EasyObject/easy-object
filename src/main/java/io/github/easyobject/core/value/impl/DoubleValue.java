/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.NumberValue;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.operator.impl.DoubleOperations;

import java.util.Objects;

/**
 * Value that encapsulates {@link Double}.
 * Supports the following arithmetical operations: addition, subtraction, multiplication, division, powering.
 * Supports unary plus and minus operators.
 * See {@link DoubleOperations} for implementation details.
 */
public class DoubleValue extends NumberValue<Double> {

    private final Double value;

    public DoubleValue(Double value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain DoubleValue}.
     * @param value value to wrap
     */
    public static DoubleValue of(Double value) {
        return new DoubleValue(value);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return DoubleOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return DoubleOperations.MINUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> multiply(Value<?> value) {
        return DoubleOperations.MULTIPLY_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> divide(Value<?> value) {
        return DoubleOperations.DIVIDE_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> pow(Value<?> value) {
        return DoubleOperations.POWER_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus() {
        return of(-value);
    }

    @Override
    public Value<?> plus() {
        return this;
    }

    @Override
    public String toString() {
        return "DoubleValue{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleValue that = (DoubleValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
