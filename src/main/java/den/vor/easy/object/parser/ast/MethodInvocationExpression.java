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
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.FunctionalValue;

import java.util.List;
import java.util.stream.Collectors;

public class MethodInvocationExpression implements Expression {

    private Expression expression;
    private Expression method;
    private List<Expression> args;

    public MethodInvocationExpression(Expression expression, Expression method, List<Expression> args) {
        this.expression = expression;
        this.method = method;
        this.args = args;
    }

    @Override
    public Value<?> eval(Variables params) {
        Value<?> value = expression.eval(params);
        Value<?> methodKey = method.eval(params);
        if (!(methodKey instanceof ScalarValue)) {
            throw new RuntimeException();
        }
        FunctionalValue<?> methodValue = value.getMethod((ScalarValue<?>) methodKey);
        List<Value<?>> argValues = args.stream().map(a -> a.eval(params)).collect(Collectors.toList());
        return methodValue.invoke(value, argValues);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public Expression getMethod() {
        return method;
    }

    public List<Expression> getArgs() {
        return args;
    }
}
