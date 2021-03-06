package den.vor.easy.object.value;

import den.vor.easy.object.value.impl.BooleanValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComparableValueTest {

    @Mock
    private Comparable<Object> first;

    @Mock
    private Comparable<Object> second;

    @Spy
    private ComparableValue<Comparable<Object>> firstValue;
    @Spy
    private ComparableValue<Comparable<Object>> secondValue;

    @BeforeEach
    public void setup() {
        when(firstValue.getValue()).thenReturn(first);
        when(secondValue.getValue()).thenReturn(second);
    }

    @ParameterizedTest
    @CsvSource({"1,true", "0,false", "-1,false"})
    public void gtShouldReturnBooleanBasedOnCompareToResult(int compareToResult, boolean expected) {
        when(first.compareTo(second)).thenReturn(compareToResult);
        BooleanValue result = firstValue.gt(secondValue);
        assertEquals(expected, result.getValue(), () -> "Expected first.gt(second) to return" + expected +
                "when firstValue.compareTo(secondValue)=" + compareToResult);
    }

    @ParameterizedTest
    @CsvSource({"1,true", "0,true", "-1,false"})
    public void gteShouldReturnBooleanBasedOnCompareToResult(int compareToResult, boolean expected) {
        when(first.compareTo(second)).thenReturn(compareToResult);
        BooleanValue result = firstValue.gte(secondValue);
        assertEquals(expected, result.getValue(), () -> "Expected first.gte(second) to return" + expected +
                "when firstValue.compareTo(secondValue)=" + compareToResult);
    }

    @ParameterizedTest
    @CsvSource({"1,false", "0,false", "-1,true"})
    public void ltShouldReturnBooleanBasedOnCompareToResult(int compareToResult, boolean expected) {
        when(first.compareTo(second)).thenReturn(compareToResult);
        BooleanValue result = firstValue.lt(secondValue);
        assertEquals(expected, result.getValue(), () -> "Expected first.lt(second) to return" + expected +
                "when firstValue.compareTo(secondValue)=" + compareToResult);
    }

    @ParameterizedTest
    @CsvSource({"1,false", "0,true", "-1,true"})
    public void lteShouldReturnBooleanBasedOnCompareToResult(int compareToResult, boolean expected) {
        when(first.compareTo(second)).thenReturn(compareToResult);
        BooleanValue result = firstValue.lte(secondValue);
        assertEquals(expected, result.getValue(), () -> "Expected first.lte(second) to return" + expected +
                "when firstValue.compareTo(secondValue)=" + compareToResult);
    }

}