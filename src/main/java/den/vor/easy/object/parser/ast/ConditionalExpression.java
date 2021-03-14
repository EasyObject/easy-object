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

import java.util.function.BinaryOperator;

public class ConditionalExpression implements Expression {

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
        return operation.function.apply(value1, value2);
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

    public enum Operation {
        EQUALS(Value::equalTo),
        NOT_EQUALS(Value::notEqualTo),

        LT(Value::lt),
        LTEQ(Value::lte),
        GT(Value::gt),
        GTEQ(Value::gte),

        AND(Value::and),
        OR(Value::or);

        private final BinaryOperator<Value<?>> function;

        Operation(BinaryOperator<Value<?>> function) {
            this.function = function;
        }
    }
}
