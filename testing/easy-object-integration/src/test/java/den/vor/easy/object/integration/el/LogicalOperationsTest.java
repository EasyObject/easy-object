package den.vor.easy.object.integration.el;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LogicalOperationsTest {

    @Test
    public void shouldReturnTrue_whenComparingTwoNumbers() {
        TestEvaluator.evaluate("2 > 1.5").assertEquals(true);
    }

    @Test
    public void shouldReturnTrue_whenComparingTwoStrings() {
        TestEvaluator.evaluate("'a' <= 'b'").assertEquals(true);
    }

    @Nested
    public class BooleanOperations {

        @Test
        public void shouldReturnFalse_whenOneOfConditionsInAndIsFalse() {
            TestEvaluator.evaluate("1 > 0 && 2 < 1").assertEquals(false);
        }

        @Test
        public void shouldReturnFalse_whenOneOfThreeConditionsInAndIsFalse() {
            TestEvaluator.evaluate("1 > 0 && 2 > 1 && 2 < 1").assertEquals(false);
        }

        @Test
        public void shouldReturnTrue_whenOneOfConditionsInAndIsTrue() {
            TestEvaluator.evaluate("1 > 0 || 2 < 1").assertEquals(true);
        }

    }
}
