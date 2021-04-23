/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value;

import com.den.vor.easy.object.value.impl.IntValue;
import com.den.vor.easy.object.value.impl.DoubleValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NumberValueTest {

    @Spy
    private NumberValue<Integer> numberValue;

    @Test
    void shouldThrowUnsupportedOperation_whenComparingWithNonNumber(@Mock Value<?> value) {
        assertThrows(UnsupportedOperationException.class, () -> numberValue.compareTo(value));

        verify(value, only()).getValue();
    }

    @Test
    void shouldDelegateToValue_whenNumbersAreInstanceOfSameClass(@Mock IntValue intValue) {
        when(numberValue.getValue()).thenReturn(1);
        when(intValue.getValue()).thenReturn(2);

        int result = numberValue.compareTo(intValue);

        assertEquals(-1, result, () -> "Expected " + numberValue + " to be less than " + intValue);

        verify(numberValue).getValue();
        verify(intValue, only()).getValue();
    }

    @Test
    void shouldCompareDoubleValues_whenNumbersAreNotInstancesOfSameClass(@Mock DoubleValue doubleValue) {
        when(numberValue.getValue()).thenReturn(1);
        when(doubleValue.getValue()).thenReturn(2.);

        int result = numberValue.compareTo(doubleValue);

        assertEquals(-1, result, () -> "Expected " + numberValue + " to be less than " + doubleValue);

        verify(numberValue).getValue();
        verify(doubleValue, only()).getValue();
    }
}
