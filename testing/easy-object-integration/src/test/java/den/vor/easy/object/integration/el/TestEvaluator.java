package den.vor.easy.object.integration.el;

import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.NullValue;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEvaluator {

    private final String expression;
    private MapValue constParams = MapValue.emptyMap();
    private Value<?> context = NullValue.NULL;

    public static TestEvaluator evaluate(String expression) {
        return new TestEvaluator(expression);    
    }
    
    public TestEvaluator(String expression) {
        this.expression = expression;
    }

    public TestEvaluator setConstParams(MapValue constParams) {
        this.constParams = constParams;
        return this;
    }

    public TestEvaluator setContext(Value<?> context) {
        this.context = context;
        return this;
    }

    public void assertEquals(Object expected) {
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(expression, constParams);
        Value<?> resultValue = expressionEvaluator.evaluate(context);
        Object value = resultValue.getValue();
        Class<?> aClass = expected.getClass();
        assertTrue(aClass.isInstance(value), () -> "Expected " + expression + " to return a result of " + aClass
                + ", when evaluated with constParams=" + constParams + ", context=" + context + ", got " + resultValue);
        Assertions.assertEquals(expected, value, () -> "Expected=" + expected + " when evaluating expression='" +
                expression + "', constParams=" + constParams + ", context=" + context + ", got " + value);
    }
}
