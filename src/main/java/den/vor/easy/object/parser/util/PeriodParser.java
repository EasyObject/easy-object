/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.util;

import den.vor.easy.object.bean.Period;

import java.util.Map;
import java.util.Optional;
import java.util.function.ObjIntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodParser {

    private static final Pattern CHUNK_PATTERN = Pattern.compile("(\\d+)([ymdhMsnw])");
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
