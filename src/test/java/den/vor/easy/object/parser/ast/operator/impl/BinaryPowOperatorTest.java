package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinaryPowOperatorTest {

    @Spy
    private BinaryPowOperator binaryPowOperator;

    @Test
    public void shouldReturnIntResult_whenEvaluatingTwoIntValues(@Mock IntValue first, @Mock IntValue second) {
        int firstInt = 4;
        int secondInt = 3;
        when(first.getValue()).thenReturn(firstInt);
        when(second.getValue()).thenReturn(secondInt);
        int expected = (int) Math.pow(firstInt, secondInt);
        int actual = evaluateAndExpectInt(first, second);

        assertEquals(expected, actual, () -> "Expected pow operator to return " + expected + " when evaluating " +
                first + "**" + second + ", got " + actual);
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnDoubleResult_whenBothArgsAreNumbersButAnyIsNotInt(Value<?> first, Value<?> second,
                                                                              double expected) {
        double actual = evaluateAndExpectDouble(first, second);

        assertEquals(expected, actual, () -> "Expected pow operator to return " + expected + " when evaluating " +
                first + "**" + second + ", got " + actual);
    }

    private static Stream<Arguments> shouldReturnDoubleResult_whenBothArgsAreNumbersButAnyIsNotInt() {
        return Stream.of(
                Arguments.of(DoubleValue.of(2.5), DoubleValue.of(5.0), Math.pow(2.5, 5)),
                Arguments.of(IntValue.of(2), DoubleValue.of(0.5), Math.pow(2, 0.5)),
                Arguments.of(DoubleValue.of(4.5), IntValue.of(2), Math.pow(4.5, 2)));
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenAnyValueIsNotNumber(Value<?> first, Value<?> second) {
        assertThrows(UnsupportedOperationException.class,
                () -> binaryPowOperator.evaluate(first, second),
                () -> "Expected binaryPowOperator to throw exception when evaluating " + first + " and " + second);
    }

    private static Stream<Arguments> shouldThrowException_whenAnyValueIsNotNumber() {
        return Stream.of(
                Arguments.of(DoubleValue.of(2.5), StringValue.of("a")),
                Arguments.of(MapValue.emptyMap(), IntValue.of(2), 4.0));
    }

    private int evaluateAndExpectInt(Value<?> first, Value<?> second) {
        Value<?> result = binaryPowOperator.evaluate(first, second);
        assertTrue(result instanceof IntValue, () -> "Expected pow operator to return int value when evaluating " +
                first + "**" + second + ", got " + result);
        return ((IntValue) result).getValue();
    }

    private double evaluateAndExpectDouble(Value<?> first, Value<?> second) {
        Value<?> result = binaryPowOperator.evaluate(first, second);
        assertTrue(result instanceof DoubleValue, () -> "Expected pow operator to return double value when " +
                "evaluating " + first + "**" + second + ", got " + result);
        return ((DoubleValue) result).getValue();
    }
}