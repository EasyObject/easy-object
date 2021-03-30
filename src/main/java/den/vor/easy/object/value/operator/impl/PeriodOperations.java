/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.PeriodValue;
import den.vor.easy.object.value.impl.StringValue;
import den.vor.easy.object.value.operator.BinaryOperator;
import den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static den.vor.easy.object.value.operator.BinaryOperatorImpl.operator;

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
            operator(Period.class, PeriodOperations::addPeriods),
            operator(LocalTime.class, (p, t) -> TimeUtil.addPeriodToTime(t, p)),
            operator(LocalDateTime.class, (p, t) -> TimeUtil.addPeriodToDateTime(t, p)),
            operator(LocalDate.class, (p, t) -> TimeUtil.addPeriodToDate(t, p)),
            operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    /**
     * Binary subtraction operator implementation. Can calculate result only when the second operand is {@link Period}.
     * Subtracts time intervals of the second operand from the first operand's. Returns {@link PeriodValue}.
     */
    public static final BinaryOperator<Period> MINUS_OPERATOR = BinaryOperator.operator(
            operator(Period.class, PeriodOperations::subtractPeriods)
    );

    /**
     * Binary subtraction operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Multiplies time intervals of the first operand by the second operand. Returns {@link PeriodValue}.
     */
    public static final BinaryOperator<Period> MULTIPLY_OPERATOR = BinaryOperator.operator(
            operator(Integer.class, PeriodOperations::multiply)
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
