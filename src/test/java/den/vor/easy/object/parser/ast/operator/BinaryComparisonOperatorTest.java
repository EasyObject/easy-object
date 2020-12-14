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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BinaryComparisonOperatorTest {

    @Nested
    public class BinaryGtOperatorTest {

        @ParameterizedTest
        @CsvSource({"2,1,true", "1,2,false", "1,1,false"})
        public void shouldCompareTwoIntsWithGtOnGtCondition(int first, int second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gt operator return " + expected
                    + " when testing " + first + ">" + second);
        }

        @ParameterizedTest
        @CsvSource({"2.5,1.5,true", "1.5,2.5,false", "1.5,1.5,false"})
        public void shouldCompareTwoDoublesOnGtCondition(double first, double second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gt operator return " + expected
                    + " when testing " + first + ">" + second);
        }

        @ParameterizedTest
        @CsvSource({"zzz,aaa,true", "aaa,zzz,false", "aaa,aaa,false", "aab,aaa,true"})
        public void shouldCompareTwoStringsOnGtCondition(String first, String second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gt operator return " + expected
                    + " when testing " + first + ">" + second);
        }
    }

    @Nested
    public class BinaryGteOperatorTest {

        @ParameterizedTest
        @CsvSource({"2,1,true", "1,2,false", "1,1,true"})
        public void shouldCompareTwoIntsOnGteCondition(int first, int second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gte operator return " + expected
                    + " when testing " + first + ">=" + second);
        }

        @ParameterizedTest
        @CsvSource({"2.5,1.5,true", "1.5,2.5,false", "1.5,1.5,true"})
        public void shouldCompareTwoDoublesOnGteCondition(double first, double second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gte operator return " + expected
                    + " when testing " + first + ">=" + second);
        }

        @ParameterizedTest
        @CsvSource({"zzz,aaa,true", "aaa,zzz,false", "aaa,aaa,true", "aab,aaa,true"})
        public void shouldCompareTwoStringsOnGteCondition(String first, String second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryGteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected gte operator return " + expected
                    + " when testing " + first + ">=" + second);
        }
    }

    @Nested
    public class BinaryLtOperatorTest {

        @ParameterizedTest
        @CsvSource({"1,2,true", "2,1,false", "1,1,false"})
        public void shouldCompareTwoIntsOnLtCondition(int first, int second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lt operator return " + expected
                    + " when testing " + first + "<" + second);
        }

        @ParameterizedTest
        @CsvSource({"1.5,2.5,true", "2.5,1.5,false", "1.5,1.5,false"})
        public void shouldCompareTwoDoublesOnLtCondition(double first, double second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lt operator return " + expected
                    + " when testing " + first + "<" + second);
        }

        @ParameterizedTest
        @CsvSource({"aaa,zzz,true", "zzz,aaa,false", "aaa,aaa,false", "aaa,aab,true"})
        public void shouldCompareTwoStringsOnLtCondition(String first, String second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLtOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lt operator return " + expected
                    + " when testing " + first + "<" + second);
        }
    }

    @Nested
    public class BinaryLteOperatorTest {

        @ParameterizedTest
        @CsvSource({"1,2,true", "2,1,false", "1,1,true"})
        public void shouldCompareTwoIntsOnLteCondition(int first, int second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lte operator return " + expected
                    + " when testing " + first + "<=" + second);
        }

        @ParameterizedTest
        @CsvSource({"1.5,2.5,true", "2.5,1.5,false", "1.5,1.5,true"})
        public void shouldCompareTwoDoublesOnLteCondition(double first, double second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lte operator return " + expected
                    + " when testing " + first + "<=" + second);
        }

        @ParameterizedTest
        @CsvSource({"aaa,zzz,true", "zzz,aaa,false", "aaa,aaa,true", "aaa,aab,true"})
        public void shouldCompareTwoStringsOnLteCondition(String first, String second, boolean expected) {
            var operator = new BinaryComparisonOperator.BinaryLteOperator();
            assertEquals(expected, operator.test(first, second), () -> "Expected lte operator return " + expected
                    + " when testing " + first + "<=" + second);
        }
    }

    @Spy
    private BinaryComparisonOperator binaryComparisonOperator;

    @Test
    public void shouldCallTestMethodWithIntArgs_whenCalledWithTwoIntValues(@Mock IntValue firstValue,
                                                                           @Mock IntValue secondValue) {
        int firstInt = 1;
        int secondInt = 2;
        when(firstValue.getValue()).thenReturn(firstInt);
        when(secondValue.getValue()).thenReturn(secondInt);

        binaryComparisonOperator.evaluate(firstValue, secondValue);

        verify(binaryComparisonOperator).test(firstInt, secondInt);
        verify(firstValue).getValue();
        verify(secondValue).getValue();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldCallTestMethodWithDoubleArgs_whenCalledWithDoubleAndNumberValues(Value<?> firstValue,
                                                                                       Value<?> secondValue,
                                                                                       double expectedFirstArg,
                                                                                       double expectedSecondArg) {
        binaryComparisonOperator.evaluate(firstValue, secondValue);

        verify(binaryComparisonOperator).test(expectedFirstArg, expectedSecondArg);
        verify(firstValue).getValue();
        verify(secondValue).getValue();
    }

    private static Stream<Arguments> shouldCallTestMethodWithDoubleArgs_whenCalledWithDoubleAndNumberValues() {
        return Stream.of(
                Arguments.of(doubleValue(2.0), doubleValue(1.0), 2.0, 1.0),
                Arguments.of(doubleValue(2.0), intValue(1), 2.0, 1.0),
                Arguments.of(intValue(2), doubleValue(1.0), 2.0, 1.0));
    }

    @Test
    public void shouldCallTestMethodWithStringArgs_whenCalledWithTwoStringValues(@Mock StringValue firstValue,
                                                                                 @Mock StringValue secondValue) {
        String firstString = "aaa";
        String secondString = "bbb";
        when(firstValue.getValue()).thenReturn(firstString);
        when(secondValue.getValue()).thenReturn(secondString);

        binaryComparisonOperator.evaluate(firstValue, secondValue);

        verify(binaryComparisonOperator).test(firstString, secondString);
        verify(firstValue).getValue();
        verify(secondValue).getValue();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldThrowUnsupportedOperationException_whenCantCompareTypes(Value<?> firstValue,
                                                                              Value<?> secondValue) {

        assertThrows(UnsupportedOperationException.class,
                () -> binaryComparisonOperator.evaluate(firstValue, secondValue),
                () -> "Expected binaryComparisonOperator to throw exception when evaluating " + firstValue + " and " +
                        secondValue);
    }

    private static Stream<Arguments> shouldThrowUnsupportedOperationException_whenCantCompareTypes() {
        return Stream.of(
                Arguments.of(StringValue.of("aaa"), IntValue.of(1)),
                Arguments.of(DoubleValue.of(1.0), StringValue.of("aaa")),
                Arguments.of(MapValue.emptyMap(), IntValue.of(1)),
                Arguments.of(DoubleValue.of(1.0), NullValue.NULL));
    }

    private static DoubleValue doubleValue(double value) {
        return spy(DoubleValue.of(value));
    }

    private static IntValue intValue(int value) {
        return spy(IntValue.of(value));
    }
}