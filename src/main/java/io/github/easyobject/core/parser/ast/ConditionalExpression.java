/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.ast;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.parser.visitors.ResultVisitor;

import java.util.function.BinaryOperator;

/**
 * Expression that encapsulates a logical binary operator and it's operands.
 * Can be used in {@link TernaryExpression}.
 */
public class ConditionalExpression implements Expression {
    /**
     * Left operand of a binary operator.
     */
    private final Expression left;
    /**
     * Right operand of a binary operator.
     */
    private final Expression right;
    /**
     * Binary operator.
     */
    private final Operator operator;

    public ConditionalExpression(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Evaluates both operands and applies their results to the operator.
     * @param variables variables to use when evaluating operands values
     * @return expression value
     */
    @Override
    public Value<?> eval(Variables variables) {
        final Value<?> value1 = left.eval(variables);
        final Value<?> value2 = right.eval(variables);
        return operator.function.apply(value1, value2);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", left, operator, right);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Operator getOperator() {
        return operator;
    }

    /**
     * Enum that describes all available operators.
     */
    public enum Operator {
        EQUALS(Value::equalTo),
        NOT_EQUALS(Value::notEqualTo),

        LT(Value::lt),
        LTEQ(Value::lte),
        GT(Value::gt),
        GTEQ(Value::gte),

        AND(Value::and),
        OR(Value::or);

        /**
         * Function that is used to calculate operator's result.
         */
        private final BinaryOperator<Value<?>> function;

        Operator(BinaryOperator<Value<?>> function) {
            this.function = function;
        }
    }
}
