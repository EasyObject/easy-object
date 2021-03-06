package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.NumberValue;
import den.vor.easy.object.value.Value;

import static den.vor.easy.object.value.operator.impl.DoubleOperations.*;

public class DoubleValue extends NumberValue<Double> {

    private final Double value;

    public static DoubleValue of(Double value) {
        return new DoubleValue(value);
    }

    public DoubleValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleValue{" +
                "value=" + value +
                '}';
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
    public Value<?> minus() {
        return of(-value);
    }

    @Override
    public Value<?> plus() {
        return this;
    }
}
