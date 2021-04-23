/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.util;

import com.den.vor.easy.object.bean.Period;

import java.util.Map;
import java.util.Optional;
import java.util.function.ObjIntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing string into a {@link Period} instance.
 */
public class PeriodParser {

    private static final Pattern CHUNK_PATTERN = Pattern.compile("(\\d{1,10})([ymdhMsnw])");
    private static final Map<String, ObjIntConsumer<Period>> PERIOD_SETTER;

    static {
        PERIOD_SETTER = Map.of(
                "y", Period::setYears,
                "m", Period::setMonths,
                "w", Period::setWeeks,
                "d", Period::setDays,
                "h", Period::setHours,
                "M", Period::setMinutes,
                "s", Period::setSeconds,
                "n", Period::setNanos
        );
    }

    /**
     * Parses string into a {@link Period} instance.
     * String should consist of number-letter pairs. A number represents a number of time intervals.
     * Letter should be a code of a time interval. Available intervals:
     *  * 'y' - Years
     *  * 'm' - Months
     *  * 'w' - Weeks
     *  * 'd' - Days
     *  * 'h' - Hours
     *  * 'M' - Minutes
     *  * 's' - Seconds
     *  * 'n' - Nanos
     * @param input string period representation
     * @return {@link Period} instance
     */
    public static Period parse(String input) {
        Period period = new Period();
        Matcher matcher = CHUNK_PATTERN.matcher(input);

        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);
            ObjIntConsumer<Period> biConsumer = Optional.ofNullable(PERIOD_SETTER.get(unit)).orElseThrow();
            biConsumer.accept(period, count);
        }
        return period;
    }

    private PeriodParser() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
