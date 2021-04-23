/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.operator.impl.PeriodOperations;
import io.github.easyobject.core.bean.Period;
import io.github.easyobject.core.value.Value;

import java.util.Objects;

import static io.github.easyobject.core.value.operator.impl.PeriodOperations.*;

/**
 * Value that encapsulates {@link Period}.
 * Supports addition, subtraction and multiplication operations.
 * See {@link PeriodOperations} for details.
 */
public class PeriodValue extends Value<Period> {

    private final Period period;

    private PeriodValue(Period period) {
        this.period = period;
    }

    /**
     * Wraps the given value with {@linkplain PeriodValue}.
     * @param period value to wrap
     */
    public static PeriodValue of(Period period) {
        return new PeriodValue(period);
    }

    @Override
    public Period getValue() {
        return period;
    }

    @Override
    public Class<?> getType() {
        return Period.class;
    }

    @Override
    public String toString() {
        return period.toString();
    }

    @Override
    public Value<?> multiply(Value<?> another) {
        return MULTIPLY_OPERATOR.apply(getValue(), another);
    }

    @Override
    public Value<?> minus(Value<?> another) {
        return MINUS_OPERATOR.apply(getValue(), another);
    }

    @Override
    public Value<?> plus(Value<?> another) {
        return PLUS_OPERATOR.apply(getValue(), another);
    }

    @Override
    public Value<?> minus() {
        Period newPeriod = new Period()
                .setYears(-period.getYears())
                .setMonths(-period.getMonths())
                .setDays(-period.getDays())
                .setHours(-period.getHours())
                .setMinutes(-period.getMinutes())
                .setSeconds(-period.getSeconds())
                .setNanos(-period.getNanos());
        return new PeriodValue(newPeriod);
    }

    @Override
    public Value<?> plus() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodValue that = (PeriodValue) o;
        return Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period);
    }
}
