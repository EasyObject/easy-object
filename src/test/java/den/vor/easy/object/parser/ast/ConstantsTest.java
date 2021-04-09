/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.ast;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static den.vor.easy.object.value.impl.IntValue.of;
import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void shouldReturnDoubleValueEqualMathPi() {
        DoubleValue pi = Constants.PI;

        assertEquals(Math.PI, pi.getValue());
    }

    @Test
    void int_shouldThrowException_whenIntFunctionHasNotOneArgument() {
        FunctionalValue<Integer> intFunction = Constants.INT;

        assertThrows(RuntimeException.class, () -> intFunction.invoke(null, List.of(of(1), of(2))));
    }

    @Test
    void int_shouldThrowException_whenIntFunctionGetsContext() {
        FunctionalValue<Integer> intFunction = Constants.INT;

        assertThrows(RuntimeException.class, () -> intFunction.invoke(of(1), List.of(of(2))));
    }

    @Test
    void int_shouldThrowException_whenOnlyArgumentIsNotSupported() {
        FunctionalValue<Integer> intFunction = Constants.INT;

        assertThrows(UnsupportedOperationException.class,
                () -> intFunction.invoke(null, List.of(MapValue.emptyMap())));
    }

    @Test
    void int_shouldReturnIntAsIs_whenOnlyArgumentIsInt() {
        FunctionalValue<Integer> intFunction = Constants.INT;
        int value = 2;

        Value<?> result = intFunction.invoke(null, List.of(of(value)));
        assertEquals(value, result.getValue(),
                () -> "Expected function to parse passed double=" + value + ", got " + result.getValue());
    }

    @Test
    void int_shouldReturnInt_whenOnlyArgumentIsDouble() {
        FunctionalValue<Integer> intFunction = Constants.INT;
        double value = 2.5;

        Value<?> result = intFunction.invoke(null, List.of(DoubleValue.of(value)));
        assertEquals((int)value, result.getValue(),
                () -> "Expected function to return passed int=" + value + ", got " + result.getValue());
    }

    @Test
    void int_shouldReturnInt_whenOnlyArgumentIsString() {
        FunctionalValue<Integer> intFunction = Constants.INT;
        String value = "2";

        Value<?> result = intFunction.invoke(null, List.of(StringValue.of(value)));
        assertEquals(Integer.valueOf(value), result.getValue(),
                () -> "Expected function to parse passed string=" + value + ", got " + result.getValue());
    }

    @Test
    void int_shouldBeIdempotent() {
        assertTrue(Constants.INT.isIdempotent(), () -> "Expected int function to be idempotent");
    }

    @Test
    void now_shouldBeNotIdempotent() {
        assertFalse(Constants.NOW.isIdempotent(), () -> "Expected int function to be not idempotent");
    }

    @Test
    void now_shouldThrowException_whenThereAreArguments() {
        FunctionalValue<LocalDateTime> now = Constants.NOW;

        assertThrows(RuntimeException.class, () -> now.invoke(null, List.of(of(1))));
    }

    @Test
    void now_shouldThrowException_whenThereIsContext() {
        FunctionalValue<LocalDateTime> now = Constants.NOW;

        assertThrows(RuntimeException.class, () -> now.invoke(of(1), List.of()));
    }

    @Test
    void now_shouldReturnCurrentDateTime() {
        FunctionalValue<LocalDateTime> now = Constants.NOW;

        Value<?> result = now.invoke(null, List.of());
        assertTrue(result instanceof DateTimeValue);
    }

}