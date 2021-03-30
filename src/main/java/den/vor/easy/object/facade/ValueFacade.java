/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.facade;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Facade utility class for creating {@link den.vor.easy.object.value.Value} instances from different types.
 */
public class ValueFacade {

    /**
     * Wraps a given BigInteger with an expression-language compatible class.
     * For operations, available in EL, see {@link BigIntValue}.
     * @param value big integer to wrap
     * @return big integer value instance
     */
    public static BigIntValue of(BigInteger value) {
        return BigIntValue.of(value);
    }

    /**
     * Wraps a given boolean with an expression-language compatible class.
     * For operations, available in EL, see {@link BooleanValue}.
     * @param value boolean to wrap
     * @return boolean value instance
     */
    public static BooleanValue of(boolean value) {
        return BooleanValue.of(value);
    }

    /**
     * Wraps a given datetime with an expression-language compatible class.
     * For operations, available in EL, see {@link DateTimeValue}.
     * @param value datetime to wrap
     * @return datetime value instance
     */
    public static DateTimeValue of(LocalDateTime value) {
        return DateTimeValue.of(value);
    }

    /**
     * Wraps a given date with an expression-language compatible class.
     * For operations, available in EL, see {@link DateValue}.
     * @param value date to wrap
     * @return date value instance
     */
    public static DateValue of(LocalDate value) {
        return DateValue.of(value);
    }

    /**
     * Wraps a given double with an expression-language compatible class.
     * For operations, available in EL, see {@link DoubleValue}.
     * @param value double to wrap
     * @return double value instance
     */
    public static DoubleValue of(double value) {
        return DoubleValue.of(value);
    }

    /**
     * Wraps a given integer with an expression-language compatible class.
     * For operations, available in EL, see {@link IntValue}.
     * @param value integer to wrap
     * @return integer value instance
     */
    public static IntValue of(int value) {
        return IntValue.of(value);
    }

    /**
     * Wraps a given list with an expression-language compatible class.
     * For operations, available in EL, see {@link ListValue}.
     * @param list list to wrap
     * @return list value instance
     */
    public static <T> ListValue<T> of(List<Value<T>> list) {
        return ListValue.of(list);
    }

    /**
     * Wraps a given list elements with an expression-language compatible class.
     * For operations, available in EL, see {@link ListValue}.
     * @param elements elements to wrap
     * @return list value instance
     */
    public static <T> ListValue<T> of(Value<T>... elements) {
        return ListValue.of(elements);
    }

    /**
     * Wraps a given map with an expression-language compatible class.
     * For operations, available in EL, see {@link MapValue}.
     * @param map map to wrap
     * @return map value instance
     */
    public static MapValue of(Map<ScalarValue<?>, Value<?>> map) {
        return MapValue.of(map);
    }

    /**
     * Creates an empty expression-language compatible map.
     * For operations, available in EL, see {@link MapValue}.
     * @return map value instance
     */
    public static MapValue of() {
        return MapValue.of();
    }

    /**
     * Wraps a given period with an expression-language compatible class.
     * For operations, available in EL, see {@link Period}.
     * @param value period to wrap
     * @return period value instance
     */
    public static PeriodValue of(Period value) {
        return PeriodValue.of(value);
    }

    /**
     * Wraps a given string with an expression-language compatible class.
     * For operations, available in EL, see {@link StringValue}.
     * @param value string to wrap
     * @return string value instance
     */
    public static StringValue of(String value) {
        return StringValue.of(value);
    }

    /**
     * Wraps a given time with an expression-language compatible class.
     * For operations, available in EL, see {@link TimeValue}.
     * @param value time to wrap
     * @return time value instance
     */
    public static TimeValue of(LocalTime value) {
        return TimeValue.of(value);
    }

    /**
     * Wraps a given uuid with an expression-language compatible class.
     * For operations, available in EL, see {@link UUIDValue}.
     * @param value uuid to wrap
     * @return uuid value instance
     */
    public static UUIDValue of(UUID value) {
        return UUIDValue.of(value);
    }

    private ValueFacade() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
