package den.vor.easy.object.integration.el;

import org.junit.jupiter.api.Test;

import static den.vor.easy.object.integration.el.TestEvaluator.evaluate;

public class OperatorOrderTest {

    @Test
    public void shouldExecuteTernaryOperatorAfterOther() {
        evaluate("(4 > 2) && (true || 3 > 2) ? 1 : 0").assertEquals(1);
    }

    @Test
    public void shouldEvaluateAndExpressionFirst_whenExpressionContainsBothAndsAndOrs() {
        TestEvaluator.evaluate("true || false && false").assertEquals(true);
    }

    @Test
    public void shouldEvaluateBitwiseXorExpressionBeforeOr() {
        TestEvaluator.evaluate("3 ^ 5 | 7").assertEquals((3 ^ 5) | 7);
    }

    @Test
    public void shouldEvaluateBitwiseAndExpressionFirst_whenExpressionContainsBitwiseAndAndXor() {
        TestEvaluator.evaluate("3 & 5 ^ 7").assertEquals((3 & 5) ^ 7);
    }

    @Test
    public void shouldEvaluateLogicalComparisonBeforeBooleanOperations() {
        TestEvaluator.evaluate("1 > 0 && 2 < 3 || 1 != 5").assertEquals((1 > 0) && (2 < 3) || (1 != 5));
    }

    @Test
    public void shouldEvaluateShiftsBeforeLogicalComparisons() {
        TestEvaluator.evaluate("1 << 3 > 10 >> 2").assertEquals((1 << 3) > (10 >> 2));
    }

    @Test
    public void shouldEvaluatePlusAndMinusBeforeShifts() {
        TestEvaluator.evaluate("3 - 2 << 5 + 4").assertEquals((3 - 2) << (5 + 4));
    }

    @Test
    public void shouldEvaluateDivisionAndMultiplicationBeforeAdditionAndSubtraction() {
        TestEvaluator.evaluate("5 * 4 - 6 / 2").assertEquals(5 * 4 - 6 / 2);
    }

    @Test
    public void shouldEvaluateDivisionMultiplicationAndModulusLeftToRight() {
        TestEvaluator.evaluate("20 % 14 * 3 / 2 % 6").assertEquals((((20 % 14) * 3) / 2) % 6);
    }

    @Test
    public void shouldEvaluateUnaryOperatorsBeforeOther() {
        TestEvaluator.evaluate("20 + -10 + +3").assertEquals(13);
    }

}
