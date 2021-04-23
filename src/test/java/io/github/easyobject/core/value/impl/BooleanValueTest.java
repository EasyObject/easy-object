/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BooleanValueTest {

    @Test
    void shouldReturnTrueAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(true);

        assertTrue(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return true as value");
    }

    @Test
    void shouldReturnFalseAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(false);

        assertFalse(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return false as value");
    }

    @Test
    void shouldReturnSameInstances_whenFactoryCalledTwiceWithSameValue() {
        BooleanValue first = BooleanValue.of(true);
        BooleanValue second = BooleanValue.of(true);

        assertSame(first, second, () -> "Expected factory method to return same object when called twice, " +
                "got " + first + " and " + second);
    }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    void shouldReturnLogicalAndOfTwoBooleans(boolean left, boolean right) {
        BooleanValue leftValue = BooleanValue.of(left);
        BooleanValue rightValue = BooleanValue.of(right);

        boolean expected = left && right;
        Value<?> resultValue = leftValue.and(rightValue);

        assertTrue(resultValue instanceof BooleanValue,
                () -> "Expected " + leftValue + "&&" + rightValue + " to return boolean value, got - " + resultValue);
        boolean result = ((BooleanValue) resultValue).getValue();
        assertEquals(expected, result,
                () -> "Expected " + leftValue + "&&" + rightValue + " to return " + expected + ", got - " + result);
    }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    void shouldReturnLogicalOrOfTwoBooleans(boolean left, boolean right) {
        BooleanValue leftValue = BooleanValue.of(left);
        BooleanValue rightValue = BooleanValue.of(right);

        boolean expected = left || right;
        Value<?> resultValue = leftValue.or(rightValue);

        assertTrue(resultValue instanceof BooleanValue,
                () -> "Expected " + leftValue + "||" + rightValue + " to return boolean value, got - " + resultValue);
        boolean result = ((BooleanValue) resultValue).getValue();
        assertEquals(expected, result,
                () -> "Expected " + leftValue + "||" + rightValue + " to return " + expected + ", got - " + result);
    }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    void shouldReturnLogicalXorOfTwoBooleans(boolean left, boolean right) {
        BooleanValue leftValue = BooleanValue.of(left);
        BooleanValue rightValue = BooleanValue.of(right);

        boolean expected = left ^ right;
        Value<?> resultValue = leftValue.xor(rightValue);

        assertTrue(resultValue instanceof BooleanValue,
                () -> "Expected " + leftValue + "^" + rightValue + " to return boolean value, got - " + resultValue);
        boolean result = ((BooleanValue) resultValue).getValue();
        assertEquals(expected, result,
                () -> "Expected " + leftValue + "^" + rightValue + " to return " + expected + ", got - " + result);
    }
}
