/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.bean;

import java.util.Objects;

/**
 * Class that represents a difference between two date time values.
 */
public class Period {
    private int years;
    private int months;
    private int weeks;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private int nanos;

    public int getWeeks() {
        return weeks;
    }

    public Period setWeeks(int weeks) {
        this.weeks = weeks;
        return this;
    }

    public int getYears() {
        return years;
    }

    public Period setYears(int years) {
        this.years = years;
        return this;
    }

    public int getMonths() {
        return months;
    }

    public Period setMonths(int months) {
        this.months = months;
        return this;
    }

    public int getDays() {
        return days;
    }

    public Period setDays(int days) {
        this.days = days;
        return this;
    }

    public int getHours() {
        return hours;
    }

    public Period setHours(int hours) {
        this.hours = hours;
        return this;
    }

    public int getMinutes() {
        return minutes;
    }

    public Period setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public int getSeconds() {
        return seconds;
    }

    public Period setSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    public int getNanos() {
        return nanos;
    }

    public Period setNanos(int nanos) {
        this.nanos = nanos;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Period period = (Period) o;
        return years == period.years &&
                months == period.months &&
                days == period.days &&
                hours == period.hours &&
                minutes == period.minutes &&
                seconds == period.seconds &&
                nanos == period.nanos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(years, months, days, hours, minutes, seconds, nanos);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (years != 0) {
            stringBuilder.append(years).append("y");
        }
        if (months != 0) {
            stringBuilder.append(months).append("m");
        }
        if (days != 0) {
            stringBuilder.append(days).append("d");
        }
        if (hours != 0) {
            stringBuilder.append(hours).append("h");
        }
        if (minutes != 0) {
            stringBuilder.append(minutes).append("M");
        }
        if (seconds != 0) {
            stringBuilder.append(seconds).append("s");
        }
        if (nanos != 0) {
            stringBuilder.append(nanos).append("n");
        }
        return stringBuilder.toString();
    }
}
