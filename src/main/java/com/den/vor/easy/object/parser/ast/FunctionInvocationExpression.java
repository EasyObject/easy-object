/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.ast;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.FunctionalValue;
import com.den.vor.easy.object.parser.exception.impl.FunctionalValueExpectedException;
import com.den.vor.easy.object.parser.visitors.ResultVisitor;
import com.den.vor.easy.object.value.impl.NullValue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Expression that represents function invocation.
 */
public class FunctionInvocationExpression implements Expression {

    /**
     * Expression that returns {@link FunctionalValue} and determines function to call.
     */
    private Expression expression;
    /**
     * List of function arguments expressions.
     */
    private List<Expression> args;

    public FunctionInvocationExpression(Expression expression, List<Expression> args) {
        this.expression = expression;
        this.args = args;
    }

    /**
     * Evaluates {@code expression} and {@code args}, then calls the function using evaluated arguments.
     * @param params variables to use during the evaluation
     * @return function invocation result
     * @throws FunctionalValueExpectedException if expression is evaluated not to {@linkplain FunctionalValue}
     */
    @Override
    public Value<?> eval(Variables params) {
        Value<?> value = expression.eval(params);
        if (!(value instanceof FunctionalValue)) {
            throw new FunctionalValueExpectedException(value);
        }
        FunctionalValue<?> functionalValue = (FunctionalValue<?>) value;
        List<Value<?>> argValues = args.stream().map(a -> a.eval(params)).collect(Collectors.toList());
        return functionalValue.invoke(NullValue.NULL, argValues);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Expression> getArgs() {
        return args;
    }

    public Expression getExpression() {
        return expression;
    }
}