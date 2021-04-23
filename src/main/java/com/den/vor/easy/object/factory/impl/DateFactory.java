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
import com.den.vor.easy.object.value.impl.DateValue;
import com.den.vor.easy.object.factory.ComparableFactory;
import com.den.vor.easy.object.factory.GenerationContext;
import com.den.vor.easy.object.factory.constraints.Bound;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Factory for {@link LocalDate} type.
 */
public class DateFactory extends ComparableFactory<LocalDate, DateValue> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final LocalDate MIN = LocalDate.parse("1970-01-01", formatter);
    public static final LocalDate MAX = LocalDate.parse("2099-12-31", formatter);

    /**
     * Creates a new factory instance.
     *
     * @param min lower inclusive bound for returned values
     * @param max upper inclusive bound for returned values
     */
    public DateFactory(LocalDate min, LocalDate max) {
        super(min, max);
    }

    public DateFactory() {
        this(MIN, MAX);
    }

    @Override
    protected LocalDate cast(Value<?> value) {
        return (LocalDate) value.getValue();
    }

    @Override
    protected DateValue getBetween(GenerationContext context, Bound<LocalDate> min, Bound<LocalDate> max) {
        long minEpoch = min.getValue().toEpochDay() + oneIfNotInclusive(min);
        long maxEpoch = max.getValue().toEpochDay() - oneIfNotInclusive(max);
        LocalDate localDate = LocalDate.ofEpochDay(context.getRandom().nextLongInclusive(minEpoch, maxEpoch));
        return DateValue.of(localDate);
    }

    private int oneIfNotInclusive(Bound<LocalDate> bound) {
        return bound.isInclusive() ? 0 : 1;
    }
}
