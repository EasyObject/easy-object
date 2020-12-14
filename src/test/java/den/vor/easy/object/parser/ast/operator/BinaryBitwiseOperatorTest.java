package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinaryBitwiseOperatorTest {

    @Nested
    public class BitwiseOrOperatorTest {

        @ParameterizedTest
        @CsvSource({"2,1", "-1,1", "0,100", "0,0", "-3,-5"})
        public void shouldReturnBitwiseOrOfTwoInts(int first, int second) {
            int expected = first | second;
            var operator = new BinaryBitwiseOperator.BitwiseOrOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected bitwise or operator to return "
                    + expected + " when evaluating " + first + "|" + second);
        }
    }

    @Nested
    public class BitwiseAndOperatorTest {

        @ParameterizedTest
        @CsvSource({"2,1", "-1,1", "0,100", "0,0", "-3,-5"})
        public void shouldReturnBitwiseAndOfTwoInts(int first, int second) {
            int expected = first & second;
            var operator = new BinaryBitwiseOperator.BitwiseAndOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected bitwise and operator to return "
                    + expected + " when evaluating " + first + "&" + second);
        }
    }

    @Nested
    public class BitwiseXorOperatorTest {

        @ParameterizedTest
        @CsvSource({"2,1", "-1,1", "0,100", "0,0", "-3,-5"})
        public void shouldReturnBitwiseXorOfTwoInts(int first, int second) {
            int expected = first ^ second;
            var operator = new BinaryBitwiseOperator.BitwiseXorOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected bitwise xor operator to return "
                    + expected + " when evaluating " + first + "^" + second);
        }
    }

    @Spy
    private BinaryBitwiseOperator binaryBitwiseOperator;

    @Test
    public void shouldCallTestMethodWithIntArgs_whenCalledWithTwoIntValues(@Mock IntValue firstValue,
                                                                           @Mock IntValue secondValue) {
        int firstInt = 1;
        int secondInt = 2;
        when(firstValue.getValue()).thenReturn(firstInt);
        when(secondValue.getValue()).thenReturn(secondInt);

        binaryBitwiseOperator.evaluate(firstValue, secondValue);

        verify(binaryBitwiseOperator).doEvaluate(firstInt, secondInt);
        verify(firstValue).getValue();
        verify(secondValue).getValue();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowUnsupportedOperationException_whenAnyValueIsNotInt(Value<?> firstValue,
                                                                              Value<?> secondValue) {

        assertThrows(UnsupportedOperationException.class,
                () -> binaryBitwiseOperator.evaluate(firstValue, secondValue),
                () -> "Expected binaryBitwiseOperator to throw exception when evaluating " + firstValue + " and " +
                        secondValue);
    }

    private static Stream<Arguments> shouldThrowUnsupportedOperationException_whenAnyValueIsNotInt() {
        return Stream.of(
                Arguments.of(StringValue.of("aaa"), IntValue.of(1)),
                Arguments.of(IntValue.of(1), StringValue.of("aaa")),
                Arguments.of(DoubleValue.of(1.0), StringValue.of("aaa")),
                Arguments.of(DoubleValue.of(1.0), IntValue.of(1)));
    }
}