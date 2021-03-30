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
import den.vor.easy.object.value.operator.impl.TimeOperations;

import java.time.LocalTime;

/**
 * Value class that encapsulates {@link LocalTime}.
 * Supports addition and subtraction operations with {@link den.vor.easy.object.bean.Period},
 * see {@link TimeOperations} for details.
 */
public class TimeValue extends ComparableValue<LocalTime> {

    private final LocalTime value;

    private TimeValue(LocalTime value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain TimeValue}.
     * @param value value to wrap
     */
    public static TimeValue of(LocalTime value) {
        return new TimeValue(value);
    }

    @Override
    public LocalTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TimeValue{" + value + '}';
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return TimeOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return TimeOperations.MINUS_OPERATOR.apply(getValue(), value);
    }
}
