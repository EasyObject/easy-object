package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.factory.PreparationContext;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

import java.util.List;

public class ElFactory extends Factory<Object, Value<Object>> {

    private ExpressionEvaluator expressionEvaluator;
    private String expression;

    public ElFactory(String expression) {
        this.expression = expression;
    }

    @Override
    protected void prepareInternal(PreparationContext preparationContext) {
        this.expressionEvaluator = new ExpressionEvaluator(expression, preparationContext.getGlobalParams());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Generator<Value<Object>> getGenerator() {
        return context -> (Value<Object>) expressionEvaluator.evaluate(context.getContext());
    }

    @Override
    public List<FieldRef> getDependencies() {
        return expressionEvaluator.getDependencies();
    }
}
