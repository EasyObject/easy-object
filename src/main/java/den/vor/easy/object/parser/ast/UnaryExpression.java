package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.ast.operator.UnaryOperator;
import den.vor.easy.object.parser.ast.operator.impl.UnaryMinusOperator;
import den.vor.easy.object.parser.ast.operator.impl.UnaryNotOperator;
import den.vor.easy.object.parser.ast.operator.impl.UnaryPlusOperator;
import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

public class UnaryExpression implements Expression {

    public enum Operation {
        NOT(new UnaryNotOperator()),
        MINUS(new UnaryMinusOperator()),
        PLUS(new UnaryPlusOperator());

        private final UnaryOperator operator;

        Operation(UnaryOperator operator) {
            this.operator = operator;
        }
    }

    private final Expression expression;
    private final Operation operation;

    public UnaryExpression(Expression expression, Operation operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public Value<?> eval(Variables variables) {
        return operation.operator.evaluate(expression.eval(variables));
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("%s %s", operation, expression);
    }

    public Operation getOperation() {
        return operation;
    }
}
