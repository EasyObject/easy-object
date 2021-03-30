/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ComparableValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.operator.impl.DateOperations;
import den.vor.easy.object.value.operator.impl.StringOperations;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * Value that encapsulates {@link String}.
 * Supports addition and multiplication operations. See {@link StringOperations} for details.
 * Uses default comparable operators implementation.
 */
public class StringValue extends ComparableValue<String> {

    private static final Map<String, FunctionalValue<String>> METHODS = Map.of(
            "size", new FunctionalValue<>((str, args) -> IntValue.of(str.getValue().length())),
            "toUpperCase", new FunctionalValue<>((str, args) -> StringValue.of(str.getValue().toUpperCase())),
            "toLowerCase", new FunctionalValue<>((str, args) -> StringValue.of(str.getValue().toLowerCase()))
    );

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain StringValue}.
     * @param value value to wrap
     */
    public static StringValue of(String value) {
        return new StringValue(value);
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
