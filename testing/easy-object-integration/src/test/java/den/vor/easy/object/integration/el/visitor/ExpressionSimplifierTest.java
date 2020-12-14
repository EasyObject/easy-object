package den.vor.easy.object.integration.el.visitor;

import org.junit.jupiter.api.Test;

public class ExpressionSimplifierTest {

    @Test
    public void shouldSimplifyBinaryExpression_whenBothChildExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("2 + 5")
                .expectValue(7);
    }

    @Test
    public void shouldSimplifyUnaryExpression_whenChildExpressionIsConstValues() {
        VisitorTestEvaluator
                .evaluate("-1")
                .expectValue(-1);
    }

    @Test
    public void shouldSimplifyBinaryLogicalExpression_whenBothChildExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("true && false")
                .expectValue(false);
    }

    @Test
    public void shouldSimplifyCompoundBinaryExpression_whenAllLeafExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("2 + 6 / 3")
                .expectValue(4);
    }

}
