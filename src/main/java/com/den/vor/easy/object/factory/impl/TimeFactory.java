/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory.impl;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.TimeValue;
import com.den.vor.easy.object.factory.ComparableFactory;
import com.den.vor.easy.object.factory.GenerationContext;
import com.den.vor.easy.object.factory.constraints.Bound;

import java.time.LocalTime;

/**
 * Factory for {@link LocalTime} type.
 */
public class TimeFactory extends ComparableFactory<LocalTime, TimeValue> {

    public TimeFactory() {
        this(LocalTime.MIN, LocalTime.MAX);
    }

    /**
     * Creates a new factory instance.
     *
     * @param min lower inclusive bound for returned values
     * @param max upper inclusive bound for returned values
     */
    public TimeFactory(LocalTime min, LocalTime max) {
        super(min, max);
    }

    @Override
    public TimeValue getBetween(GenerationContext context, Bound<LocalTime> min, Bound<LocalTime> max) {
        LocalTime minTime = min.getValue().plusNanos(oneIfNotInclusive(min));
        LocalTime maxTime = max.getValue().minusNanos(oneIfNotInclusive(max));
        long minNano = minTime.toNanoOfDay();
        long maxNano = maxTime.toNanoOfDay();
        LocalTime localTime = randomBetween(context, minNano, maxNano);
        return TimeValue.of(localTime);
    }

    @Override
    protected LocalTime cast(Value<?> value) {
        return (LocalTime) value.getValue();
    }

    private int oneIfNotInclusive(Bound<?> bound) {
        return bound.isInclusive() ? 0 : 1;
    }

    private LocalTime randomBetween(GenerationContext context, long min, long max) {
        long sec = context.getRandom().nextLongInclusive(min, max);
        return LocalTime.ofNanoOfDay(sec);
    }
}
