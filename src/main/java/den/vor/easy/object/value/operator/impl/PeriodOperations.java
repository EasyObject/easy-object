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
import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class PeriodOperations {

    public static final Operator<Period> PLUS_OPERATOR_REGISTRY = Operator.operator(
            operator(Period.class, PeriodOperations::addPeriods),
            operator(LocalTime.class, (p, t) -> TimeUtil.addPeriodToTime(t, p)),
            operator(LocalDateTime.class, (p, t) -> TimeUtil.addPeriodToDateTime(t, p)),
            operator(LocalDate.class, (p, t) -> TimeUtil.addPeriodToDate(t, p)),
            operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<Period> MINUS_OPERATOR_REGISTRY = Operator.operator(
            operator(Period.class, PeriodOperations::subtractPeriods)
    );

    public static final Operator<Period> MULTIPLY_OPERATOR_REGISTRY = Operator.operator(
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
