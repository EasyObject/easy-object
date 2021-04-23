/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors.impl;


import io.github.easyobject.core.parser.visitors.AbstractOptimizationVisitor;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.FunctionalValue;
import io.github.easyobject.core.parser.ast.*;

import java.util.Collection;

/**
 * AST nodes visitor that replaces function invocation with it's result if the function is.
 * {@link FunctionalValue#isIdempotent()} and all arguments are constants
 */
public class IdempotentFunctionCallsFoldingVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public IdempotentFunctionCallsFoldingVisitor(Variables variables) {
        this.variables = variables;
    }

    /**
     * If {@link FunctionInvocationExpression} is an idempotent and all arguments are known or the compile time,
     * replaces the call with an actual value.
     * Example: {@code int('12')} will be replaced with a single value.
     * @param expression expression to visit
     * @return original or evaluated expression
     */
    @Override
    public Expression visit(FunctionInvocationExpression expression) {
        if (isExpressionIdempotentFunction(expression.getExpression()) && allArgsAreValues(expression.getArgs())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    /**
     * Replaces method invocation with an actual result if all conditions apply:
     * * method context expression is a {@link ValueExpression}.
     * * method is idempotent.
     * * all arguments are {@link ValueExpression}s (if any).
     * Example: {@code someList.size()} will be replaced with a single value.
     * @param expression expression to visit
     * @return original or evaluated expression
     */
    @Override
    public Expression visit(MethodInvocationExpression expression) {
        if (isValue(expression.getExpression()) &&
                isExpressionIdempotentFunction(expression.getMethod()) &&
                allArgsAreValues(expression.getArgs())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    private boolean allArgsAreValues(Collection<Expression> arguments) {
        return arguments.stream().allMatch(this::isValue);
    }

    private boolean isExpressionIdempotentFunction(Expression expression) {
        if (!(expression instanceof ValueExpression)) {
            return false;
        }
        Value<?> value = ((ValueExpression) expression).getValue();
        if (!(value instanceof FunctionalValue)) {
            return false;
        }
        FunctionalValue<?> functionalValue = (FunctionalValue<?>) value;
        return functionalValue.isIdempotent();
    }
}
