package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.ast.operator.*;
import den.vor.easy.object.parser.ast.operator.impl.*;
import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

public class BinaryExpression implements Expression {

    public enum Operation {
        PLUS(new BinaryPlusOperator()),
        MINUS(new BinaryMinusOperator()),
        DIVIDE(new BinaryDivideOperator()),
        MULTIPLY(new BinaryStarOperator()),
        POW(new BinaryPowOperator()),
        REMAINDER(new BinaryReminderOperator());

        private final BinaryOperator operator;

        Operation(BinaryOperator operator) {
            this.operator = operator;
        }
    }

    private final Expression left;
    private final Expression right;
    private final Operation operation;

    public BinaryExpression(Expression left,
                            Expression right,
                            Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "[" + left + " " + operation + " " + right + "]";
    }

    @Override
    public Value<?> eval(Variables variables) {
        return operation.operator.evaluate(left.eval(variables), right.eval(variables));
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Operation getOperation() {
        return operation;
    }
}
