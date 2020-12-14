package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;
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
public class BinaryPlusOperatorTest {

    @Spy
    private BinaryPlusOperator binaryPlusOperator;

    @Test
    public void shouldReturnIntResult_whenEvaluatingTwoIntValues(@Mock IntValue first, @Mock IntValue second) {
        int firstInt = 4;
        int secondInt = 2;
        when(first.getValue()).thenReturn(firstInt);
        when(second.getValue()).thenReturn(secondInt);
        int expected = firstInt + secondInt;
        int actual = evaluateAndExpectInt(first, second);

        assertEquals(expected, actual, () -> "Expected plus operator to return " + expected + " when evaluating " +
                first + "+" + second + ", got " + actual);
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnDoubleResult_whenBothArgsAreNumbersButAnyIsNotInt(Value<?> first, Value<?> second,
                                                                              double expected) {
        double actual = evaluateAndExpectDouble(first, second);

        assertEquals(expected, actual, () -> "Expected plus operator to return " + expected + " when evaluating " +
                first + "+" + second + ", got " + actual);
    }

    private static Stream<Arguments> shouldReturnDoubleResult_whenBothArgsAreNumbersButAnyIsNotInt() {
        return Stream.of(
                Arguments.of(DoubleValue.of(2.5), DoubleValue.of(5.0), 7.5),
                Arguments.of(IntValue.of(2), DoubleValue.of(0.5), 2.5),
                Arguments.of(DoubleValue.of(4.5), IntValue.of(2), 6.5));
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnStringResult_whenAnyArgIsString(Value<?> first, Value<?> second, String expected) {
        String actual = evaluateAndExpectString(first, second);

        assertEquals(expected, actual, () -> "Expected plus operator to return " + expected + " when evaluating " +
                first + "+" + second + ", got " + actual);
    }

    private static Stream<Arguments> shouldReturnStringResult_whenAnyArgIsString() {
        return Stream.of(
                Arguments.of(StringValue.of("a"), StringValue.of("b"), "ab"),
                Arguments.of(IntValue.of(2), StringValue.of("a"), "2a"),
                Arguments.of(StringValue.of("a"), DoubleValue.of(4.5), "a4.5"));
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenAnyValueIsNotNumberAndNotString(Value<?> first, Value<?> second) {
        assertThrows(UnsupportedOperationException.class,
                () -> binaryPlusOperator.evaluate(first, second),
                () -> "Expected binaryPlusOperator to throw exception when evaluating " + first + " and " + second);
    }

    private static Stream<Arguments> shouldThrowException_whenAnyValueIsNotNumberAndNotString() {
        return Stream.of(
                Arguments.of(DoubleValue.of(2.5), NullValue.NULL),
                Arguments.of(MapValue.emptyMap(), IntValue.of(2)));
    }

    private int evaluateAndExpectInt(Value<?> first, Value<?> second) {
        Value<?> result = binaryPlusOperator.evaluate(first, second);
        assertTrue(result instanceof IntValue, () -> "Expected plus operator to return int value when evaluating " +
                first + "+" + second + ", got " + result);
        return ((IntValue) result).getValue();
    }

    private double evaluateAndExpectDouble(Value<?> first, Value<?> second) {
        Value<?> result = binaryPlusOperator.evaluate(first, second);
        assertTrue(result instanceof DoubleValue, () -> "Expected plus operator to return double value when " +
                "evaluating " + first + "+" + second + ", got " + result);
        return ((DoubleValue) result).getValue();
    }

    private String evaluateAndExpectString(Value<?> first, Value<?> second) {
        Value<?> result = binaryPlusOperator.evaluate(first, second);
        assertTrue(result instanceof StringValue, () -> "Expected plus operator to return string value when " +
                "evaluating " + first + "+" + second + ", got " + result);
        return ((StringValue) result).getValue();
    }

}