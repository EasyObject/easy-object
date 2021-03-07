/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors.impl;


import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.FunctionalValue;

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

    @Override
    public Expression visit(FunctionInvocationExpression expression) {
        if (isExpressionIdempotentFunction(expression.getExpression()) && allArgsAreValues(expression.getArgs())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

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
