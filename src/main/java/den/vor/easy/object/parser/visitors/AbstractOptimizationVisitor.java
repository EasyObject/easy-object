package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractOptimizationVisitor implements OptimizationVisitor {

    @Override
    public Expression visit(BinaryExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(ConditionalExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(UnaryExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(ValueExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(ContextVariableAccessExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(TernaryExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(MethodInvocationExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(FunctionInvocationExpression expression) {
        return expression;
    }

    @Override
    public Expression visit(VariableMapAccessExpression expression) {
        return expression;
    }

    protected boolean isValue(Expression e) {
        return e instanceof ValueExpression;
    }
}
