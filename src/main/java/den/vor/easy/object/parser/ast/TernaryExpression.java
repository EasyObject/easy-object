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

public class TernaryExpression implements Expression {

    private final Expression condition;
    private final Expression thenExpression;
    private final Expression elseExpression;

    public TernaryExpression(Expression condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

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
