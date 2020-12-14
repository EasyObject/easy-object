package den.vor.easy.object.integration.el.visitor;

import den.vor.easy.object.parser.ast.ValueExpression;
import org.junit.jupiter.api.Test;

public class IdempotentFunctionCallsFoldingVisitorTest {

    @Test
    public void shouldFoldCastFunction_whenArgumentIsConst() {
        VisitorTestEvaluator.evaluate("int('12')")
                .expectValue(12);
    }
}
