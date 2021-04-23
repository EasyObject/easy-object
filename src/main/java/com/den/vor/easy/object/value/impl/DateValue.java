/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.impl;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.ComparableValue;
import com.den.vor.easy.object.value.operator.impl.DateOperations;

import java.time.LocalDate;

/**
 * Value that encapsulates {@link LocalDate}.
 * Supports addition and subtraction operation. See {@link DateOperations} for details.
 * Uses default comparable operators implementation.
 */
public class DateValue extends ComparableValue<LocalDate> {

    private final LocalDate value;

    private DateValue(LocalDate value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain DateValue}.
     * @param value value to wrap
     */
    public static DateValue of(LocalDate value) {
        return new DateValue(value);
    }

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DateValue{" + value + '}';
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return DateOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return DateOperations.MINUS_OPERATOR.apply(getValue(), value);
    }
}
