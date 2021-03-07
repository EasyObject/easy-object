/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.ComparableFactory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.DateTimeValue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Factory for {@link LocalDateTime} type.
 */
public class DatetimeFactory extends ComparableFactory<LocalDateTime, DateTimeValue> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSSSSS");
    public static final LocalDateTime MIN = LocalDateTime.parse("1970-01-01 00:00:00:000000", formatter);
    public static final LocalDateTime MAX = LocalDateTime.parse("2099-12-31 23:59:59:999999", formatter);

    private static final ZoneOffset TIMEZONE = ZoneOffset.UTC;

    public DatetimeFactory() {
        this(MIN, MAX);
    }

    /**
     * Creates a new factory instance.
     *
     * @param min lower inclusive bound for returned values
     * @param max upper inclusive bound for returned values
     */
    public DatetimeFactory(LocalDateTime min, LocalDateTime max) {
        super(min, max);
    }

    @Override
    public DateTimeValue getBetween(GenerationContext context,
                                    Bound<LocalDateTime> minBound,
                                    Bound<LocalDateTime> maxBound) {
        LocalDateTime min = minBound.getValue().plusNanos(oneIfNotInclusive(minBound));
        LocalDateTime max = maxBound.getValue().minusNanos(oneIfNotInclusive(maxBound));
        long sec = context.getRandom().nextLongInclusive(min.toEpochSecond(TIMEZONE), max.toEpochSecond(TIMEZONE));
        int nanos = context.getRandom().nextIntInclusive(min.getNano(), max.getNano());
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(sec, nanos, TIMEZONE);
        return DateTimeValue.of(localDateTime);
    }

    @Override
    protected LocalDateTime cast(Value<?> value) {
        return (LocalDateTime) value.getValue();
    }

    private int oneIfNotInclusive(Bound<LocalDateTime> bound) {
        return bound.isInclusive() ? 0 : 1;
    }


}
