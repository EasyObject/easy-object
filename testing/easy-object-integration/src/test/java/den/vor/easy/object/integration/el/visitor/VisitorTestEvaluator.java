package den.vor.easy.object.integration.el.visitor;

import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.ValueExpression;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisitorTestEvaluator {

    private final String expression;
    private MapValue constParams = MapValue.emptyMap();

    public static VisitorTestEvaluator evaluate(String expression) {
        return new VisitorTestEvaluator(expression);
    }

    private VisitorTestEvaluator(String expression) {
        this.expression = expression;
    }

    private VisitorTestEvaluator withConstParams(MapValue constParams) {
        this.constParams = constParams;
        return this;
    }

    public <T> void expectValue(T expected) {
        Expression optimized = new ExpressionEvaluator(expression, constParams).getExpression();

        assertTrue(optimized instanceof ValueExpression, () -> "Expected expression='" + expression + "' to be " +
                "optimized to ValueExpression, but got " + optimized);
        Value<?> value = ((ValueExpression) optimized).getValue();
        Object valueObject = value.getValue();
        assertTrue(expected.getClass().isInstance(valueObject), () -> "Expected " + expression
                + " to return a result of Integer, got " + valueObject);
        assertEquals(expected, valueObject, () -> "Expected=" + expected + " when evaluating expression='" +
                expression + "', got " + valueObject);

    }
}
