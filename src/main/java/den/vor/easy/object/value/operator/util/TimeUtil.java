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

/**
 * Utility class that provides arithmetical operations with time values.
 */
public class TimeUtil {

    /**
     * Adds a period to a time. Result is trimmed to be less than one day.
     * @param time time to add period to
     * @param period period to add
     * @return addition result
     */
    public static TimeValue addPeriodToTime(LocalTime time, Period period) {
        LocalTime sum = time
                .plusHours(period.getHours())
                .plusMinutes(period.getMinutes())
                .plusSeconds(period.getSeconds())
                .plusNanos(period.getNanos());

        return TimeValue.of(sum);
    }

    /**
     * Adds a period to a dateTime.
     * @param dateTime dateTime to add period to
     * @param period period to add
     * @return addition result
     */
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

    /**
     * Adds a period to a date. Time intervals less than a day are ignored.
     * @param date date to add period to
     * @param period period to add
     * @return addition result
     */
    public static DateValue addPeriodToDate(LocalDate date, Period period) {
        LocalDate sum = date
                .plusYears(period.getYears())
                .plusWeeks(period.getWeeks())
                .plusMonths(period.getMonths())
                .plusDays(period.getDays());

        return DateValue.of(sum);
    }

    /**
     * Subtracts a period from a time. Result is trimmed to be less than one day.
     * @param time time to subtract period from
     * @param period period to subtract
     * @return subtraction result
     */
    public static TimeValue subtractPeriodFromTime(LocalTime time, Period period) {
        LocalTime result = time
                .minusHours(period.getHours())
                .minusMinutes(period.getMinutes())
                .minusSeconds(period.getSeconds())
                .minusNanos(period.getNanos());

        return TimeValue.of(result);
    }

    /**
     * Subtracts a period from a dateTime.
     * @param dateTime dateTime to subtract period from
     * @param period period to subtract
     * @return subtraction result
     */
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

    /**
     * Subtracts a period from a date. Time intervals less than a day are ignored.
     * @param date date to subtract period from
     * @param period period to subtract
     * @return subtraction result
     */
    public static DateValue subtractPeriodFromDate(LocalDate date, Period period) {
        LocalDate result = date
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
