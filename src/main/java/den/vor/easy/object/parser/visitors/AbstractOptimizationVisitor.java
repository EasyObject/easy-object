package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractOptimizationVisitor implements OptimizationVisitor {

    @Override
    public Expression visit(BinaryExpression s) {
        return s;
    }

    @Override
    public Expression visit(ConditionalExpression s) {
        return s;
    }

    @Override
    public Expression visit(UnaryExpression s) {
        return s;
    }

    @Override
    public Expression visit(ValueExpression s) {
        return s;
    }

    @Override
    public Expression visit(ContextVariableAccessExpression s) {
        return s;
    }

    @Override
    public Expression visit(TernaryExpression s) {
        return s;
    }

    @Override
    public Expression visit(MethodInvocationExpression s) {
        return s;
    }

    @Override
    public Expression visit(FunctionInvocationExpression s) {
        return s;
    }

    @Override
    public Expression visit(VariableMapAccessExpression s) {
        return s;
    }

    protected boolean isValue(Expression e) {
        return e instanceof ValueExpression;
    }
}
