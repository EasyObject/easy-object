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
public class BinaryReminderOperatorTest {

    @Spy
    private BinaryReminderOperator binaryReminderOperator;

    @Test
    public void shouldReturnIntResult_whenEvaluatingTwoIntValues(@Mock IntValue first, @Mock IntValue second) {
        int firstInt = 13;
        int secondInt = 7;
        when(first.getValue()).thenReturn(firstInt);
        when(second.getValue()).thenReturn(secondInt);
        int expected = firstInt % secondInt;
        int actual = evaluateAndExpectInt(first, second);

        assertEquals(expected, actual, () -> "Expected reminder operator to return " + expected + " when evaluating " +
                first + "%" + second + ", got " + actual);
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowException_whenAnyValueIsNotInt(Value<?> first, Value<?> second) {
        assertThrows(UnsupportedOperationException.class,
                () -> binaryReminderOperator.evaluate(first, second),
                () -> "Expected binaryReminderOperator to throw exception when evaluating " + first + " and " + second);
    }

    private static Stream<Arguments> shouldThrowException_whenAnyValueIsNotInt() {
        return Stream.of(
                Arguments.of(DoubleValue.of(2.5), IntValue.of(2)),
                Arguments.of(IntValue.of(2), DoubleValue.of(1.0)));
    }

    private int evaluateAndExpectInt(Value<?> first, Value<?> second) {
        Value<?> result = binaryReminderOperator.evaluate(first, second);
        assertTrue(result instanceof IntValue, () -> "Expected reminder operator to return int value when evaluating " +
                first + "%" + second + ", got " + result);
        return ((IntValue) result).getValue();
    }
}