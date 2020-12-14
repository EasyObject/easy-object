package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

public class DoubleValue extends Value<Double> {

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
}
