package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ComparableValue;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.operator.impl.StringOperations;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class StringValue extends ComparableValue<String> {

    private static final Map<String, FunctionalValue<String>> METHODS = Map.of(
            "size", new FunctionalValue<>((str, args) -> IntValue.of(str.getValue().length())),
            "toUpperCase", new FunctionalValue<>((str, args) -> StringValue.of(str.getValue().toUpperCase())),
            "toLowerCase", new FunctionalValue<>((str, args) -> StringValue.of(str.getValue().toLowerCase()))
    );

    private final String value;

    public static StringValue of(String value) {
        return new StringValue(value);
    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return this;
    }

    @Override
    public String toString() {
        return "StringValue{" + value + '}';
    }

    @Override
    protected Map<String, FunctionalValue<String>> getMethods() {
        return METHODS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return StringOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> multiply(Value<?> value) {
        return StringOperations.MULTIPLY_OPERATOR.apply(getValue(), value);
    }
}
