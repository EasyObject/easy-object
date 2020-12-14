package den.vor.easy.object.parser.visitors.impl;


import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.FunctionalValue;

import java.util.Collection;

public class IdempotentFunctionCallsFoldingVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public IdempotentFunctionCallsFoldingVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(FunctionInvocationExpression s) {
        s = (FunctionInvocationExpression) super.visit(s);

        if (isExpressionIdempotentFunction(s.getExpression()) && allArgsAreValues(s.getArgs())) {
            return new ValueExpression(s.eval(variables));
        }
        return s;
    }

    @Override
    public Expression visit(MethodInvocationExpression s) {
        s = (MethodInvocationExpression) super.visit(s);

        if (isValue(s.getExpression()) &&
                isExpressionIdempotentFunction(s.getMethod()) &&
                allArgsAreValues(s.getArgs())) {
            return new ValueExpression(s.eval(variables));
        }
        return s;
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
