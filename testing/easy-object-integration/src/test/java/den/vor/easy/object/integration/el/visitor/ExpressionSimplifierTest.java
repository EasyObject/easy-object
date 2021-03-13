/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.el.visitor;

import org.junit.jupiter.api.Test;

class ExpressionSimplifierTest {

    @Test
    void shouldSimplifyBinaryExpression_whenBothChildExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("2 + 5")
                .expectValue(7);
    }

    @Test
    void shouldSimplifyUnaryExpression_whenChildExpressionIsConstValues() {
        VisitorTestEvaluator
                .evaluate("-1")
                .expectValue(-1);
    }

    @Test
    void shouldSimplifyBinaryLogicalExpression_whenBothChildExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("true && false")
                .expectValue(false);
    }

    @Test
    void shouldSimplifyCompoundBinaryExpression_whenAllLeafExpressionsAreConstValues() {
        VisitorTestEvaluator
                .evaluate("2 + 6 / 3")
                .expectValue(4);
    }

}
