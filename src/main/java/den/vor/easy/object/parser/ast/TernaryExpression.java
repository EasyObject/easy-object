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
        return condition.eval(variables).as(Boolean.class) ? thenExpression.eval(variables) : elseExpression.eval(variables);
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
