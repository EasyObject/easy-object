package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UnaryMinusOperatorTest {

    @Spy
    private UnaryMinusOperator unaryMinusOperator;

    @ParameterizedTest
    @MethodSource
    public void shouldReturnIntValue_whenEvaluatingInt(IntValue value, int expected) {
        int actual = evaluateAndExpectInt(value);

        assertEquals(expected, actual, () -> "Expected minus operator to return " + expected + " when evaluating -" +
                value + ", got " + actual);
    }

    private static Stream<Arguments> shouldReturnIntValue_whenEvaluatingInt() {
        return Stream.of(
                Arguments.of(IntValue.of(1), -1),
                Arguments.of(IntValue.of(-1), 1),
                Arguments.of(IntValue.of(0), 0));
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnDoubleValue_whenEvaluatingDouble(DoubleValue value, double expected) {
        double actual = evaluateAndExpectDouble(value);

        assertEquals(expected, actual, () -> "Expected minus operator to return " + expected + " when evaluating -" +
                value + ", got " + actual);
    }

    private static Stream<Arguments> shouldReturnDoubleValue_whenEvaluatingDouble() {
        return Stream.of(
                Arguments.of(DoubleValue.of(1.0), -1.0),
                Arguments.of(DoubleValue.of(-1.0), 1.0),
                Arguments.of(DoubleValue.of(0.0), -0.0)); // according to IEEE 754 standard
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenCanNotNegateValue(Value<?> first) {
        assertThrows(UnsupportedOperationException.class,
                () -> unaryMinusOperator.evaluate(first),
                () -> "Expected unaryMinusOperator to throw exception when evaluating " + first);
    }

    private static Stream<Arguments> shouldThrowException_whenCanNotNegateValue() {
        return Stream.of(
                Arguments.of(NullValue.NULL),
                Arguments.of(MapValue.emptyMap()),
                Arguments.of(StringValue.of("someString")),
                Arguments.of(BooleanValue.TRUE));
    }

    private int evaluateAndExpectInt(Value<?> first) {
        Value<?> result = unaryMinusOperator.evaluate(first);
        assertTrue(result instanceof IntValue, () -> "Expected minus operator to return int value when evaluating -" +
                first + ", got " + result);
        return ((IntValue) result).getValue();
    }

    private double evaluateAndExpectDouble(Value<?> first) {
        Value<?> result = unaryMinusOperator.evaluate(first);
        assertTrue(result instanceof DoubleValue, () -> "Expected minus operator to return double value when " +
                "evaluating -" + first + ", got " + result);
        return ((DoubleValue) result).getValue();
    }
}