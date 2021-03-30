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

import java.util.function.UnaryOperator;

/**
 * Expression that encapsulates an unary operator and it's operand.
 */
public class UnaryExpression implements Expression {

    /**
     * Expression, which result is used as an operand.
     */
    private final Expression expression;
    /**
     * Unary operator.
     */
    private final Operator operator;

    public UnaryExpression(Expression expression, Operator operator) {
        this.expression = expression;
        this.operator = operator;
    }

    /**
     * Evaluates an expression and applies it's result to the operator.
     * @param variables variables to use during the evaluation
     * @return unary operator invocation result
     */
    @Override
    public Value<?> eval(Variables variables) {
        return operator.function.apply(expression.eval(variables));
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
        return "UnaryExpression{" + operator + expression + '}';
    }

    public Operator getOperator() {
        return operator;
    }

    /**
     * Enum that describes all unary operators.
     */
    public enum Operator {
        NOT(Value::not),
        MINUS(Value::minus),
        PLUS(Value::plus);

        /**
         * Function that is used to calculate operator's result.
         */
        private final UnaryOperator<Value<?>> function;

        Operator(UnaryOperator<Value<?>> function) {
            this.function = function;
        }
    }
}
