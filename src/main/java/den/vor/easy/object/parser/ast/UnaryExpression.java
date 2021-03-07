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

import java.util.function.Function;

public class UnaryExpression implements Expression {

    private final Expression expression;
    private final Operation operation;
    public UnaryExpression(Expression expression, Operation operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public Value<?> eval(Variables variables) {
        return operation.function.apply(expression.eval(variables));
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
        return "UnaryExpression{" + operation + expression + '}';
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        NOT(Value::not),
        MINUS(Value::minus),
        PLUS(Value::plus);

        private final Function<Value<?>, Value<?>> function;

        Operation(Function<Value<?>, Value<?>> function) {
            this.function = function;
        }
    }
}
