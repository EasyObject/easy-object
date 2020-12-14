package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UnaryNotOperatorTest {

    @Spy
    private UnaryNotOperator unaryNotOperator;

    @Test
    public void shouldReturnTrue_whenEvaluatingFalse() {
        BooleanValue value = BooleanValue.FALSE;
        boolean actual = evaluateAndExpectBoolean(value);

        assertTrue(actual, () -> "Expected not operator to return true when evaluating -" + value + ", got " + actual);
    }

    @Test
    public void shouldReturnFalse_whenEvaluatingTrue() {
        BooleanValue value = BooleanValue.TRUE;
        boolean actual = evaluateAndExpectBoolean(value);

        assertFalse(actual, () -> "Expected not operator to return true when evaluating -" + value + ", got " + actual);
    }


    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenCanNotMultiplyValues(Value<?> first) {
        assertThrows(UnsupportedOperationException.class,
                () -> unaryNotOperator.evaluate(first),
                () -> "Expected unaryNotOperator to throw exception when evaluating " + first);
    }

    private static Stream<Arguments> shouldThrowException_whenCanNotMultiplyValues() {
        return Stream.of(
                Arguments.of(DoubleValue.of(1.0)),
                Arguments.of(MapValue.emptyMap()),
                Arguments.of(StringValue.of("someString")),
                Arguments.of(IntValue.of(1)));
    }

    private boolean evaluateAndExpectBoolean(Value<?> first) {
        Value<?> result = unaryNotOperator.evaluate(first);
        assertTrue(result instanceof BooleanValue, () -> "Expected minus operator to return int value when " +
                "evaluating -" + first + ", got " + result);
        return ((BooleanValue) result).getValue();
    }
}