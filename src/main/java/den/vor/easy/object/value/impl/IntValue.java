/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.NumberValue;
import den.vor.easy.object.value.Value;

import java.util.Objects;

import static den.vor.easy.object.value.operator.impl.IntOperations.*;

public class IntValue extends NumberValue<Integer> {

    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public static IntValue of(int value) {
        return new IntValue(value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return StringValue.of(String.valueOf(value));
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return MINUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> multiply(Value<?> value) {
        return MULTIPLY_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> divide(Value<?> value) {
        return DIVIDE_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> pow(Value<?> value) {
        return POWER_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> remainder(Value<?> value) {
        return REMAINDER_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> shiftLeft(Value<?> value) {
        return LEFT_SHIFT_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> shiftRight(Value<?> value) {
        return RIGHT_SHIFT_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> bitwiseAnd(Value<?> value) {
        return BITWISE_AND_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> bitwiseOr(Value<?> value) {
        return BITWISE_OR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> bitwiseXor(Value<?> value) {
        return BITWISE_XOR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus() {
        return IntValue.of(-value);
    }

    @Override
    public Value<?> plus() {
        return this;
    }

    @Override
    public String toString() {
        return "IntValue{" + value + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
