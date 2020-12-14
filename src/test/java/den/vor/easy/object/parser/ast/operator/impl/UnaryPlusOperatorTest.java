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

@ExtendWith(MockitoExtension.class)
public class UnaryPlusOperatorTest {

    @Spy
    private UnaryPlusOperator unaryPlusOperator;

    @Test
    public void shouldReturnSameObject_whenEvaluatingIntValue(@Mock IntValue value) {
        Value<?> actual = unaryPlusOperator.evaluate(value);

        assertSame(value, actual, () -> "Expected plus operator to return same value when evaluating -" + value
                + ", got " + actual);
    }

    @Test
    public void shouldReturnSameObject_whenEvaluatingDoubleValue(@Mock DoubleValue value) {
        Value<?> actual = unaryPlusOperator.evaluate(value);

        assertSame(value, actual, () -> "Expected plus operator to return same value when evaluating -" + value
                + ", got " + actual);
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenCanNotMultiplyValues(Value<?> first) {
        assertThrows(UnsupportedOperationException.class,
                () -> unaryPlusOperator.evaluate(first),
                () -> "Expected unaryNotOperator to throw exception when evaluating " + first);
    }

    private static Stream<Arguments> shouldThrowException_whenCanNotMultiplyValues() {
        return Stream.of(
                Arguments.of(NullValue.NULL),
                Arguments.of(MapValue.emptyMap()),
                Arguments.of(StringValue.of("someString")),
                Arguments.of(BooleanValue.TRUE));
    }
}