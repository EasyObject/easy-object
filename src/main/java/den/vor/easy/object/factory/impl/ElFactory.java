/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.factory.PreparationContext;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;

import java.util.List;

/**
 * Factory, which {@link Generator} evaluates passed string expression for every object.
 *
 * @param <T> type of returned value
 */
public class ElFactory<T> extends Factory<T, Value<T>> {

    private final String expression;
    private final Class<T> tClass;
    private ExpressionEvaluator expressionEvaluator;

    private ElFactory(String expression, Class<T> tClass) {
        this.expression = expression;
        this.tClass = tClass;
    }

    /**
     * Creates a new instance of factory.
     *
     * @param expression string to evaluate
     * @param tClass     class object that specifies of generated value
     * @param <T>        type of generated value
     * @return new factory instance
     */
    public static <T> ElFactory<T> factory(String expression, Class<T> tClass) {
        return new ElFactory<>(expression, tClass);
    }

    public static ElFactory<Object> factory(String expression) {
        return factory(expression, Object.class);
    }

    @Override
    protected void prepareInternal(PreparationContext preparationContext) {
        this.expressionEvaluator = new ExpressionEvaluator(expression, preparationContext.getGlobalParams());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Generator<Value<T>> getGenerator() {
        if (tClass.equals(Object.class)) {
            return context -> (Value<T>) expressionEvaluator.evaluate(context.getContext());
        }
        return context -> checkAndCast(expressionEvaluator.evaluate(context.getContext()));
    }

    @Override
    public List<FieldRef> getDependencies() {
        return expressionEvaluator.getDependencies();
    }

    @SuppressWarnings("unchecked")
    private Value<T> checkAndCast(Value<?> value) {
        Object expressionResult = value.getValue();

        if (!tClass.isInstance(expressionResult)) {
            throw new ClassCastException("Expected result of type " + tClass + ", got " + expressionResult);
        }
        return (Value<T>) value;
    }
}
