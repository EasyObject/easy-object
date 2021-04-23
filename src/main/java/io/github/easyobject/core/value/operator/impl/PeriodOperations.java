/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.operator.impl;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.PeriodValue;
import io.github.easyobject.core.value.operator.BinaryOperator;
import io.github.easyobject.core.value.operator.BinaryOperatorImpl;
import io.github.easyobject.core.bean.Period;
import io.github.easyobject.core.value.impl.StringValue;
import io.github.easyobject.core.value.operator.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class that contains operator implementation for {@link PeriodValue}.
 */
public class PeriodOperations {

    /**
     * Binary addition operator implementation. Can calculate result when the second operand is:
     * * {@link Period} - sums both periods time intervals. Returns {@link PeriodValue}.
     * * {@link LocalTime} - adds current period to the time.
     * See {@link TimeUtil#addPeriodToTime(LocalTime, Period)} for details. Returns {@link LocalTime}.
     * * {@link LocalDateTime} - adds current period to the time.
     * See {@link TimeUtil#addPeriodToDateTime(LocalDateTime, Period)} for details. Returns {@link LocalDateTime}.
     * * {@link LocalDate} - adds current period to the time.
     * See {@link TimeUtil#addPeriodToDate(LocalDate, Period)} for details. Returns {@link LocalDate}.
     * * {@link String} - converts current value to string and adds the second operand. Returns {@link StringValue}.
     */
    public static final BinaryOperator<Period> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, PeriodOperations::addPeriods),
            BinaryOperatorImpl.operator(LocalTime.class, (p, t) -> TimeUtil.addPeriodToTime(t, p)),
            BinaryOperatorImpl.operator(LocalDateTime.class, (p, t) -> TimeUtil.addPeriodToDateTime(t, p)),
            BinaryOperatorImpl.operator(LocalDate.class, (p, t) -> TimeUtil.addPeriodToDate(t, p)),
            BinaryOperatorImpl.operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    /**
     * Binary subtraction operator implementation. Can calculate result only when the second operand is {@link Period}.
     * Subtracts time intervals of the second operand from the first operand's. Returns {@link PeriodValue}.
     */
    public static final BinaryOperator<Period> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, PeriodOperations::subtractPeriods)
    );

    /**
     * Binary subtraction operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Multiplies time intervals of the first operand by the second operand. Returns {@link PeriodValue}.
     */
    public static final BinaryOperator<Period> MULTIPLY_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, PeriodOperations::multiply)
    );

    private static Value<?> subtractPeriods(Period left, Period right) {
        Period difference = new Period()
                .setYears(left.getYears() - right.getYears())
                .setMonths(left.getMonths() - right.getMonths())
                .setDays(left.getDays() - right.getDays())
                .setHours(left.getHours() - right.getHours())
                .setMinutes(left.getMinutes() - right.getMinutes())
                .setSeconds(left.getSeconds() - right.getSeconds())
                .setNanos(left.getNanos() - right.getNanos());
        return PeriodValue.of(difference);
    }

    private static Value<?> addPeriods(Period left, Period right) {
        Period sum = new Period()
                .setYears(left.getYears() + right.getYears())
                .setMonths(left.getMonths() + right.getMonths())
                .setDays(left.getDays() + right.getDays())
                .setHours(left.getHours() + right.getHours())
                .setMinutes(left.getMinutes() + right.getMinutes())
                .setSeconds(left.getSeconds() + right.getSeconds())
                .setNanos(left.getNanos() + right.getNanos());
        return PeriodValue.of(sum);
    }

    private static Value<?> multiply(Period period, int multiplier) {
        Period newPeriod = new Period()
                .setYears(period.getYears() * multiplier)
                .setMonths(period.getMonths() * multiplier)
                .setDays(period.getDays() * multiplier)
                .setHours(period.getHours() * multiplier)
                .setMinutes(period.getMinutes() * multiplier)
                .setSeconds(period.getSeconds() * multiplier)
                .setNanos(period.getNanos() * multiplier);
        return PeriodValue.of(newPeriod);
    }

    private PeriodOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
