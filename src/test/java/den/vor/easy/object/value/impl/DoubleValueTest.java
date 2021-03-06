package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.OperationAware;
import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;
import static org.junit.jupiter.api.Assertions.*;

public class DoubleValueTest {

    @Nested
    public class SumTest {
        @Test
        public void shouldReturnDouble_whenAddingIntToDouble() {
            test(DoubleValue.of(5.0), IntValue.of(10))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DoubleValue.of(15.0));
        }

        @Test
        public void shouldReturnDouble_whenAddingIntAndDoubleValues() {
            test(DoubleValue.of(5.0), DoubleValue.of(10.0))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DoubleValue.of(15.0));
        }

        @Test
        public void shouldReturnString_whenAddingIntAndStringValues() {
            test(DoubleValue.of(5.0), StringValue.of("a"))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(StringValue.of("5.0a"));
        }
    }

    @Test
    public void shouldReturnDouble_whenSubtractingNumberFromDoubleValues() {
        test(DoubleValue.of(5.0), IntValue.of(10))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(DoubleValue.of(-5.0));
    }

    @Test
    public void shouldReturnDouble_whenMultiplyingDoubleByNumber() {
        test(DoubleValue.of(5.0), IntValue.of(10))
                .withFunction(OperationAware::multiply)
                .withFunctionSymbol("*")
                .verifyEquals(DoubleValue.of(50.0));
    }

    @Test
    public void shouldReturnDouble_whenDividingDobuleByNumber() {
        test(DoubleValue.of(5.0), IntValue.of(2))
                .withFunction(OperationAware::divide)
                .withFunctionSymbol("/")
                .verifyEquals(DoubleValue.of(2.5));
    }

    @Test
    public void shouldReturnDouble_whenTakingPowerDoubleAndNumber() {
        test(DoubleValue.of(5.0), IntValue.of(3))
                .withFunction(OperationAware::pow)
                .withFunctionSymbol("**")
                .verifyEquals(DoubleValue.of(125.0));
    }

    @Test
    public void shouldReturnDoubleWithNegativeValue_whenCalledMinusMethod() {
        DoubleValue value = DoubleValue.of(3.0);

        Value<?> negative = value.minus();
        Object negativeValue = negative.getValue();

        assertTrue(negativeValue instanceof Double,
                () -> "Expected -" + value + " to return double, got - " + negativeValue);
        assertEquals(-3.0, negativeValue, () -> "Expected -" + value + " to return -3, got - " + negativeValue);
    }

    @Test
    public void shouldReturnSameInstance_whenCalledPlusMethod() {
        DoubleValue value = DoubleValue.of(3.0);

        Value<?> plusValue = value.plus();

        assertSame(value, plusValue, () -> "Expected +" + value + " to return same instance, got " + plusValue);
    }
}