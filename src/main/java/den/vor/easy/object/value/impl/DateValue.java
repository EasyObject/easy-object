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

import java.time.LocalDate;

public class DateValue extends ComparableValue<LocalDate> {

    private final LocalDate value;

    private DateValue(LocalDate value) {
        this.value = value;
    }

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
