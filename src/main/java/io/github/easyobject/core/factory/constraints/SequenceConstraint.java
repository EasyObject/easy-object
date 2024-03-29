/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.constraints;


import io.github.easyobject.core.factory.ComparableFactory;
import io.github.easyobject.core.parser.ExpressionEvaluator;

/**
 * Specifies constraints for {@link ComparableFactory}.
 *
 * @param <T> type of object generated by the factory
 */
public abstract class SequenceConstraint<T extends Comparable<? super T>> {

    private final ExpressionEvaluator expressionEvaluator;

    /**
     * Creates a new constraint instance.
     *
     * @param constraint expression that returns constraint
     */
    protected SequenceConstraint(String constraint) {
        this.expressionEvaluator = new ExpressionEvaluator(constraint);
    }

    /**
     * Applies constraint on given {@link SequenceConstraintsValues}.
     */
    public abstract SequenceConstraintsValues<T> apply(SequenceConstraintsValues<T> constraintsValues, T value);

    public ExpressionEvaluator getExpressionEvaluator() {
        return expressionEvaluator;
    }
}
