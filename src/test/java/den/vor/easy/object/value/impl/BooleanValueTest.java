package den.vor.easy.object.value.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanValueTest {

    @Test
    public void shouldReturnTrueAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(true);

        assertTrue(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return true as value");
    }

    @Test
    public void shouldReturnFalseAsValue_whenTruePassedToFactory() {
        BooleanValue booleanValue = BooleanValue.of(false);

        assertFalse(booleanValue.getValue(), () -> "Expected booleanValue=" + booleanValue + " return false as value");
    }

    @Test
    public void shouldReturnSameInstances_whenFactoryCalledTwiceWithSameValue() {
        BooleanValue first = BooleanValue.of(true);
        BooleanValue second = BooleanValue.of(true);

        assertSame(first, second, () -> "Expected factory method to return same object when called twice, " +
                "got " + first + " and " + second);
    }
}