/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.ast;

import com.den.vor.easy.object.parser.exception.impl.ScalarValueExpectedException;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.FunctionalValue;
import com.den.vor.easy.object.parser.visitors.ResultVisitor;
import com.den.vor.easy.object.value.ScalarValue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Expression that represents method invocation.
 */
public class MethodInvocationExpression implements Expression {

    /**
     * Expression, to call method on.
     */
    private Expression expression;
    /**
     * Method name expression. Must evaluate to a {@link ScalarValue}.
     */
    private Expression method;
    /**
     * List of arguments of a method.
     */
    private List<Expression> args;

    public MethodInvocationExpression(Expression expression, Expression method, List<Expression> args) {
        this.expression = expression;
        this.method = method;
        this.args = args;
    }

    /**
     * Evaluates {@code expression}, {@code method} and {@code args}, then calls the method.
     * @param params variables to use during the evaluation
     * @return method invocation result
     * @throws ScalarValueExpectedException if method key resolved to non-scalar value
     */
    @Override
    public Value<?> eval(Variables params) {
        Value<?> value = expression.eval(params);
        Value<?> methodKey = method.eval(params);
        if (!(methodKey instanceof ScalarValue)) {
            throw new ScalarValueExpectedException(methodKey);
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
