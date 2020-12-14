package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinaryLogicalOperatorTest {

    private static final String SOURCE_METHOD_NAME =
            "den.vor.easy.object.parser.ast.operator.BinaryLogicalOperatorTest#twoBoolPairs";

    @Nested
    public class BitwiseOrOperatorTest {

        @ParameterizedTest
        @MethodSource(SOURCE_METHOD_NAME)
        public void shouldReturnLogicalOrOfTwoBooleans(boolean first, boolean second) {
            boolean expected = first || second;
            var operator = new BinaryLogicalOperator.LogicalOrOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected logical or operator to return "
                    + expected + " when evaluating " + first + "||" + second);
        }
    }

    @Nested
    public class BitwiseAndOperatorTest {

        @ParameterizedTest
        @MethodSource(SOURCE_METHOD_NAME)
        public void shouldReturnLogicalAndOfTwoBooleans(boolean first, boolean second) {
            boolean expected = first && second;
            var operator = new BinaryLogicalOperator.LogicalAndOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected logical and operator to return "
                    + expected + " when evaluating " + first + "&&" + second);
        }
    }

    @Nested
    public class BitwiseXorOperatorTest {

        @ParameterizedTest
        @MethodSource(SOURCE_METHOD_NAME)
        public void shouldReturnLogicalXorOfTwoBooleans(boolean first, boolean second) {
            boolean expected = first ^ second;
            var operator = new BinaryLogicalOperator.LogicalXorOperator();
            assertEquals(expected, operator.doEvaluate(first, second), () -> "Expected logical xor operator to return "
                    + expected + " when evaluating " + first + "^" + second);
        }
    }


    private static Stream<Arguments> twoBoolPairs() {
        List<Boolean> booleans = List.of(true, false);
        return booleans.stream().flatMap(b1 -> booleans.stream().map(b2 -> Arguments.of(b1, b2)));
    }

    @Spy
    private BinaryLogicalOperator binaryLogicalOperator;

    @Test
    public void shouldCallTestMethodWithIntArgs_whenCalledWithTwoIntValues(@Mock BooleanValue firstValue,
                                                                           @Mock BooleanValue secondValue) {
        when(firstValue.getValue()).thenReturn(true);
        when(secondValue.getValue()).thenReturn(false);

        binaryLogicalOperator.evaluate(firstValue, secondValue);

        verify(binaryLogicalOperator).doEvaluate(true, false);
        verify(firstValue).getValue();
        verify(secondValue).getValue();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowUnsupportedOperationException_whenAnyValueIsNotBoolean(Value<?> firstValue,
                                                                                  Value<?> secondValue) {

        assertThrows(UnsupportedOperationException.class,
                () -> binaryLogicalOperator.evaluate(firstValue, secondValue),
                () -> "Expected binaryLogicalOperator to throw exception when evaluating " + firstValue + " and " +
                        secondValue);
    }

    private static Stream<Arguments> shouldThrowUnsupportedOperationException_whenAnyValueIsNotBoolean() {
        return Stream.of(
                Arguments.of(BooleanValue.of(true), IntValue.of(1)),
                Arguments.of(StringValue.of("aaa"), BooleanValue.of(false)),
                Arguments.of(DoubleValue.of(1.0), StringValue.of("aaa")));
    }
}