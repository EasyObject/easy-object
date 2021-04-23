/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.ast;

import com.den.vor.easy.object.value.OperationAware;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.parser.visitors.ResultVisitor;

import java.util.function.BinaryOperator;

/**
 * Expression that encapsulates a binary operator and it's operands.
 */
public class BinaryExpression implements Expression {

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

    public BinaryExpression(Expression left,
                            Expression right,
                            Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "[" + left + " " + operator + " " + right + "]";
    }

    /**
     * Evaluates both operands and applies their results to the operator.
     * @param variables variables to use when evaluating operands values
     * @return expression value
     */
    @Override
    public Value<?> eval(Variables variables) {
        return operator.function.apply(left.eval(variables), right.eval(variables));
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

    public Operator getOperator() {
        return operator;
    }

    /**
     * Enum that describes all available operators.
     */
    public enum Operator {
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

        /**
         * Function that is used to calculate operator's result.
         */
        private final BinaryOperator<Value<?>> function;

        Operator(BinaryOperator<Value<?>> function) {
            this.function = function;
        }
    }
}
