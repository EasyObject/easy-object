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
import den.vor.easy.object.value.operator.impl.DateTimeOperations;

import java.time.LocalDateTime;

/**
 * Value that encapsulates {@link LocalDateTime}.
 * Supports addition and subtraction operation. See {@link DateTimeOperations} for details.
 * Uses default comparable operators implementation.
 */
public class DateTimeValue extends ComparableValue<LocalDateTime> {

    private final LocalDateTime value;

    private DateTimeValue(LocalDateTime value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain DateTimeValue}.
     * @param value value to wrap
     */
    public static DateTimeValue of(LocalDateTime value) {
        return new DateTimeValue(value);
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DateTimeValue{" + value + '}';
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return DateTimeOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return DateTimeOperations.MINUS_OPERATOR.apply(getValue(), value);
    }
}
