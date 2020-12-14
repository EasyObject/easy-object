package den.vor.easy.object.parser.ast.operator;


import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinaryNotEqOperatorTest {

    @Spy
    private BinaryNotEqOperator binaryNotEqOperator;

    @Test
    public void shouldReturnFalse_whenTestingObjectAgainstItself(@Mock Value<Object> value) {
        when(value.getValue()).thenReturn(new Object());
        boolean result = evaluate(value, value);
        assertFalse(result, "Expected not eq operator to return false when value is tested against itself");
    }

    @Test
    public void shouldReturnFalse_whenContainedValuesEqual(@Mock Value<Integer> first, @Mock Value<Integer> second) {
        int value = 1;
        when(first.getValue()).thenReturn(value);
        when(second.getValue()).thenReturn(value);
        boolean result = evaluate(first, second);
        assertFalse(result, "Expected not eq operator to return false when contained values are equal");
    }

    @Test
    public void shouldReturnFalse_whenBothValuesContainNull(@Mock Value<?> first, @Mock Value<?> second) {
        boolean result = evaluate(first, second);
        assertFalse(result, "Expected not eq operator to return false when both values contain null");
    }

    @Test
    public void shouldReturnTrue_whenContainedValuesNotEqual(@Mock Value<Integer> first, @Mock Value<Integer> second) {
        when(first.getValue()).thenReturn(1);
        when(second.getValue()).thenReturn(2);
        boolean result = evaluate(first, second);
        assertTrue(result, "Expected not eq operator to return true when first.equals(second) returned false");
    }

    private boolean evaluate(Value<?> first, Value<?> second) {
        Value<?> result = binaryNotEqOperator.evaluate(first, second);
        assertTrue(result instanceof BooleanValue, () -> "Expected eq operator to return boolean value, got " + result);
        return ((BooleanValue) result).getValue();
    }
}