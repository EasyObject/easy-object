package den.vor.easy.object.value.impl;


import den.vor.easy.object.value.OperationAware;
import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class IntValueTest {

    @Test
    public void shouldReturnStringValueContainingInt() {
        IntValue intValue = IntValue.of(10);
        StringValue stringValue = intValue.toStringValue();

        assertThat(stringValue.getValue(), equalTo("10"));
    }

    @Nested
    public class SumTest {
        @Test
        public void shouldReturnInt_whenAddingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(IntValue.of(15));
        }

        @Test
        public void shouldReturnDouble_whenAddingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DoubleValue.of(15.0));
        }

        @Test
        public void shouldReturnString_whenAddingIntAndStringValues() {
            test(IntValue.of(5), StringValue.of("a"))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(StringValue.of("5a"));
        }
    }

    @Nested
    public class SubtractionTest {
        @Test
        public void shouldReturnInt_whenSubtractingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::minus)
                    .withFunctionSymbol("-")
                    .verifyEquals(IntValue.of(-5));
        }

        @Test
        public void shouldReturnDouble_whenSubtractingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::minus)
                    .withFunctionSymbol("-")
                    .verifyEquals(DoubleValue.of(-5.0));
        }
    }

    @Nested
    public class MultiplicationTest {
        @Test
        public void shouldReturnInt_whenMultiplyingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(10))
                    .withFunction(OperationAware::multiply)
                    .withFunctionSymbol("*")
                    .verifyEquals(IntValue.of(50));
        }

        @Test
        public void shouldReturnDouble_whenMultiplyingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::multiply)
                    .withFunctionSymbol("*")
                    .verifyEquals(DoubleValue.of(50.0));
        }
    }

    @Nested
    public class DivisionTest {
        @Test
        public void shouldReturnInt_whenDividingTwoIntValues() {
            test(IntValue.of(5), IntValue.of(2))
                    .withFunction(OperationAware::divide)
                    .withFunctionSymbol("/")
                    .verifyEquals(IntValue.of(2));
        }

        @Test
        public void shouldReturnDouble_whenDividingIntAndDoubleValues() {
            test(IntValue.of(5), DoubleValue.of(10.0))
                    .withFunction(OperationAware::divide)
                    .withFunctionSymbol("/")
                    .verifyEquals(DoubleValue.of(0.5));
        }
    }

    @Nested
    public class PowerTest {
        @Test
        public void shouldReturnInt_whenTakingPowerFromTwoIntValues() {
            test(IntValue.of(5), IntValue.of(3))
                    .withFunction(OperationAware::pow)
                    .withFunctionSymbol("**")
                    .verifyEquals(IntValue.of(125));
        }

        @Test
        public void shouldReturnDouble_whenTakingPowerFromIntAndDoubleValues() {
            test(IntValue.of(2), DoubleValue.of(10.0))
                    .withFunction(OperationAware::pow)
                    .withFunctionSymbol("**")
                    .verifyEquals(DoubleValue.of(1024.0));
        }
    }

    @Test
    public void shouldReturnInt_whenTakingRemainderFromTwoIntValues() {
        test(IntValue.of(5), IntValue.of(2))
                .withFunction(OperationAware::remainder)
                .withFunctionSymbol("%")
                .verifyEquals(IntValue.of(1));
    }

    @Test
    public void shouldReturnInt_whenShiftingLeftIntValueByIntValue() {
        test(IntValue.of(5), IntValue.of(2))
                .withFunction(OperationAware::shiftLeft)
                .withFunctionSymbol("<<")
                .verifyEquals(IntValue.of(20));
    }

    @Test
    public void shouldReturnInt_whenShiftingRightIntValueByIntValue() {
        test(IntValue.of(10), IntValue.of(2))
                .withFunction(OperationAware::shiftRight)
                .withFunctionSymbol(">>")
                .verifyEquals(IntValue.of(2));
    }

    @Test
    public void shouldReturnInt_whenTakingBitwiseAndBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseAnd)
                .withFunctionSymbol("&")
                .verifyEquals(IntValue.of(2));
    }

    @Test
    public void shouldReturnInt_whenTakingBitwiseOrBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseOr)
                .withFunctionSymbol("|")
                .verifyEquals(IntValue.of(3));
    }

    @Test
    public void shouldReturnInt_whenTakingBitwiseXorBetweenTwoIntValues() {
        test(IntValue.of(3), IntValue.of(2))
                .withFunction(OperationAware::bitwiseXor)
                .withFunctionSymbol("^")
                .verifyEquals(IntValue.of(1));
    }

    @Test
    public void shouldReturnIntWithNegativeValue_whenCalledMinusMethod() {
        IntValue value = IntValue.of(3);

        Value<?> negative = value.minus();
        Object negativeValue = negative.getValue();

        assertTrue(negativeValue instanceof Integer,
                () -> "Expected -" + value + " to return int, got - " + negativeValue);
        assertEquals(-3, negativeValue, () -> "Expected -" + value + " to return -3, got - " + negativeValue);
    }

    @Test
    public void shouldReturnSameInstance_whenCalledPlusMethod() {
        IntValue value = IntValue.of(3);

        Value<?> plusValue = value.plus();

        assertSame(value, plusValue, () -> "Expected +" + value + " to return same instance, got " + plusValue);
    }
}