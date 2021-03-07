/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.Value;

import static den.vor.easy.object.value.operator.impl.PeriodOperations.*;

public class PeriodValue extends Value<Period> {

    private final Period period;

    private PeriodValue(Period period) {
        this.period = period;
    }

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
        return MULTIPLY_OPERATOR_REGISTRY.apply(getValue(), another);
    }

    @Override
    public Value<?> minus(Value<?> another) {
        return MINUS_OPERATOR_REGISTRY.apply(getValue(), another);
    }

    @Override
    public Value<?> plus(Value<?> another) {
        return PLUS_OPERATOR_REGISTRY.apply(getValue(), another);
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
}
