/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

/**
 * Expression that evaluates on of it's branches depending on the condition.
 */
public class TernaryExpression implements Expression {

    /**
     * Condition expression. Result must be castable to {@code boolean}.
     */
    private final Expression condition;
    /**
     * Expression that is evaluated if condition is {@code true}.
     */
    private final Expression thenExpression;
    /**
     * Expression that is evaluated if condition is {@code false}.
     */
    private final Expression elseExpression;

    public TernaryExpression(Expression condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    /**
     * Evaluates condition and one of branches based on condition result.
     * @param variables variables to use during the evaluation
     * @return evaluation result of {@code thenExpression} if condition is true, {@code elseExpression}'s result
     * otherwise.
     */
    @Override
    public Value<?> eval(Variables variables) {
        Boolean conditionValue = condition.eval(variables).as(Boolean.class);
        if (Boolean.TRUE.equals(conditionValue)) {
            return thenExpression.eval(variables);
        }
        return elseExpression.eval(variables);
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getThenExpression() {
        return thenExpression;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return condition + " ? " + thenExpression + " : " + elseExpression;
    }
}
