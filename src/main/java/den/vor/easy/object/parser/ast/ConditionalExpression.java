package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.ast.operator.*;
import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

public final class ConditionalExpression implements Expression {

    public enum Operation {
        EQUALS(new BinaryEqOperator()),
        NOT_EQUALS(new BinaryNotEqOperator()),

        LT(new BinaryComparisonOperator.BinaryLtOperator()),
        LTEQ(new BinaryComparisonOperator.BinaryLteOperator()),
        GT(new BinaryComparisonOperator.BinaryGtOperator()),
        GTEQ(new BinaryComparisonOperator.BinaryGteOperator()),

        AND(new BinaryLogicalOperator.LogicalAndOperator()),
        OR(new BinaryLogicalOperator.LogicalOrOperator());

        private final BinaryOperator operator;

        Operation(BinaryOperator operator) {
            this.operator = operator;
        }
    }

    private final Expression left;
    private final Expression right;
    private final Operation operation;

    public ConditionalExpression(Expression left, Expression right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public Value<?> eval(Variables variables) {
        final Value<?> value1 = left.eval(variables);
        final Value<?> value2 = right.eval(variables);
        return operation.operator.evaluate(value1, value2);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", left, operation, right);
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
