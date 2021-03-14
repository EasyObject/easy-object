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
import den.vor.easy.object.value.OperationAware;
import den.vor.easy.object.value.Value;

import java.util.function.BinaryOperator;

public class BinaryExpression implements Expression {

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
        return operation.function.apply(left.eval(variables), right.eval(variables));
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

    public enum Operation {
        PLUS(OperationAware::plus),
        MINUS(OperationAware::minus),
        DIVIDE(OperationAware::divide),
        MULTIPLY(OperationAware::multiply),
        POW(OperationAware::pow),
        REMAINDER(OperationAware::remainder),
        LEFT_SHIFT(OperationAware::shiftLeft),
        RIGHT_SHIFT(OperationAware::shiftRight),
        AND(OperationAware::bitwiseAnd),
        OR(OperationAware::bitwiseOr),
        XOR(OperationAware::bitwiseXor);

        private final BinaryOperator<Value<?>> function;

        Operation(BinaryOperator<Value<?>> function) {
            this.function = function;
        }
    }
}
