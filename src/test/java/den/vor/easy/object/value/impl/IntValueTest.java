/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;


import den.vor.easy.object.value.OperationAware;
import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class IntValueTest {

    @Test
    void shouldReturnStringValueContainingInt() {
        IntValue intValue = IntValue.of(10);
        StringValue stringValue = intValue.toStringValue();

        assertThat(stringValue.getValue(), equalTo("10"));
    }

    @Nested
    class SumTest {
        @Test
        void shouldReturnInt_whenAddingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(IntValue.of(15));
        }

        @Test
        void shouldReturnDouble_whenAddingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DoubleValue.of(15.0));
        }

        @Test
        void shouldReturnString_whenAddingIntAndStringValues() {
            test(IntValue.of(5), StringValue.of("a"))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(StringValue.of("5a"));
        }
    }

    @Nested
    class SubtractionTest {
        @Test
        void shouldReturnInt_whenSubtractingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::minus)
                    .withFunctionSymbol("-")
                    .verifyEquals(IntValue.of(-5));
        }

        @Test
        void shouldReturnDouble_whenSubtractingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::minus)
                    .withFunctionSymbol("-")
                    .verifyEquals(DoubleValue.of(-5.0));
        }
    }

    @Nested
    class MultiplicationTest {
        @Test
        void shouldReturnInt_whenMultiplyingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::multiply)
                    .withFunctionSymbol("*")
                    .verifyEquals(IntValue.of(50));
        }

        @Test
        void shouldReturnDouble_whenMultiplyingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::multiply)
                    .withFunctionSymbol("*")
                    .verifyEquals(DoubleValue.of(50.0));
        }
    }

    @Nested
    class DivisionTest {
        @Test
        void shouldReturnInt_whenDividingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(2))
                    .withFunction(OperationAware::divide)
                    .withFunctionSymbol("/")
                    .verifyEquals(IntValue.of(2));
        }

        @Test
        void shouldReturnDouble_whenDividingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::divide)
                    .withFunctionSymbol("/")
                    .verifyEquals(DoubleValue.of(0.5));
        }
    }

    @Nested
    class PowerTest {
        @Test
        void shouldReturnInt_whenTakingPowerFromTwoIntValues() {
            test(IntValue.of(5), IntValue.of(3))
                    .withFunction(OperationAware::pow)
                    .withFunctionSymbol("**")
                    .verifyEquals(IntValue.of(125));
        }

        @Test
        void shouldReturnDouble_whenTakingPowerFromIntAndDoubleValues() {
            test(IntValue.of(2), DoubleValue.of(10.0))
                    .withFunction(OperationAware::pow)
                    .withFunctionSymbol("**")
                    .verifyEquals(DoubleValue.of(1024.0));
        }
    }

    @Test
    void shouldReturnInt_whenTakingRemainderFromTwoIntValues() {
        test(IntValue.of(5), IntValue.of(2))
                .withFunction(OperationAware::remainder)
                .withFunctionSymbol("%")
                .verifyEquals(IntValue.of(1));
    }

    @Test
    void shouldReturnInt_whenShiftingLeftIntValueByIntValue() {
        test(IntValue.of(5), IntValue.of(2))
                .withFunction(OperationAware::shiftLeft)
                .withFunctionSymbol("<<")
                .verifyEquals(IntValue.of(20));
    }

    @Test
    void shouldReturnInt_whenShiftingRightIntValueByIntValue() {
        test(IntValue.of(10), IntValue.of(2))
                .withFunction(OperationAware::shiftRight)
                .withFunctionSymbol(">>")
                .verifyEquals(IntValue.of(2));
    }

    @Test
    void shouldReturnInt_whenTakingBitwiseAndBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseAnd)
                .withFunctionSymbol("&")
                .verifyEquals(IntValue.of(2));
    }

    @Test
    void shouldReturnInt_whenTakingBitwiseOrBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseOr)
                .withFunctionSymbol("|")
                .verifyEquals(IntValue.of(3));
    }

    @Test
    void shouldReturnInt_whenTakingBitwiseXorBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseXor)
                .withFunctionSymbol("^")
                .verifyEquals(IntValue.of(1));
    }

    @Test
    void shouldReturnIntWithNegativeValue_whenCalledMinusMethod() {
        IntValue value = IntValue.of(3);

        Value<?> negative = value.minus();
        Object negativeValue = negative.getValue();

        assertTrue(negativeValue instanceof Integer,
                () -> "Expected -" + value + " to return int, got - " + negativeValue);
        assertEquals(-3, negativeValue, () -> "Expected -" + value + " to return -3, got - " + negativeValue);
    }

    @Test
    void shouldReturnSameInstance_whenCalledPlusMethod() {
        IntValue value = IntValue.of(3);

        Value<?> plusValue = value.plus();

        assertSame(value, plusValue, () -> "Expected +" + value + " to return same instance, got " + plusValue);
    }
}
