package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanValueTest {

    @Test
    public void shouldReturnTrueAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(true);

        assertTrue(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return true as value");
    }

    @Test
    public void shouldReturnFalseAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(false);

        assertFalse(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return false as value");
    }

    @Test
    public void shouldReturnSameInstances_whenFactoryCalledTwiceWithSameValue() {
        BooleanValue first = BooleanValue.of(true);
        BooleanValue second = BooleanValue.of(true);

        assertSame(first, second, () -> "Expected factory method to return same object when called twice, " +
                "got " + first + " and " + second);
    }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    public void shouldReturnLogicalAndOfTwoBooleans(boolean left, boolean right) {
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
    public void shouldReturnLogicalOrOfTwoBooleans(boolean left, boolean right) {
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
    public void shouldReturnLogicalXorOfTwoBooleans(boolean left, boolean right) {
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