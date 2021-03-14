/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.util;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.impl.DateTimeValue;
import den.vor.easy.object.value.impl.DateValue;
import den.vor.easy.object.value.impl.TimeValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public static TimeValue addPeriodToTime(LocalTime time, Period period) {
        LocalTime sum = time
                .plusHours(period.getHours())
                .plusMinutes(period.getMinutes())
                .plusSeconds(period.getSeconds())
                .plusNanos(period.getNanos());

        return TimeValue.of(sum);
    }

    public static DateTimeValue addPeriodToDateTime(LocalDateTime dateTime, Period period) {
        LocalDateTime sum = dateTime
                .plusYears(period.getYears())
                .plusMonths(period.getMonths())
                .plusWeeks(period.getWeeks())
                .plusDays(period.getDays())
                .plusHours(period.getHours())
                .plusMinutes(period.getMinutes())
                .plusSeconds(period.getSeconds())
                .plusNanos(period.getNanos());

        return DateTimeValue.of(sum);
    }

    public static DateValue addPeriodToDate(LocalDate dateTime, Period period) {
        LocalDate sum = dateTime
                .plusYears(period.getYears())
                .plusWeeks(period.getWeeks())
                .plusMonths(period.getMonths())
                .plusDays(period.getDays());

        return DateValue.of(sum);
    }

    public static TimeValue subtractPeriodFromTime(LocalTime time, Period period) {
        LocalTime result = time
                .minusHours(period.getHours())
                .minusMinutes(period.getMinutes())
                .minusSeconds(period.getSeconds())
                .minusNanos(period.getNanos());

        return TimeValue.of(result);
    }

    public static DateTimeValue subtractPeriodFromDateTime(LocalDateTime dateTime, Period period) {
        LocalDateTime result = dateTime
                .minusYears(period.getYears())
                .minusMonths(period.getMonths())
                .minusWeeks(period.getWeeks())
                .minusDays(period.getDays())
                .minusHours(period.getHours())
                .minusMinutes(period.getMinutes())
                .minusSeconds(period.getSeconds())
                .minusNanos(period.getNanos());

        return DateTimeValue.of(result);
    }

    public static DateValue subtractPeriodFromDate(LocalDate dateTime, Period period) {
        LocalDate result = dateTime
                .minusYears(period.getYears())
                .minusWeeks(period.getWeeks())
                .minusMonths(period.getMonths())
                .minusDays(period.getDays());

        return DateValue.of(result);
    }

    private TimeUtil() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
