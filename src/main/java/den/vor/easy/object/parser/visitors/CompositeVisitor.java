package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

import java.util.List;

public class CompositeVisitor extends DelegatingOptimizationVisitor {

    private final List<AbstractOptimizationVisitor> optimizationVisitors;

    public CompositeVisitor(List<AbstractOptimizationVisitor> optimizationVisitors) {
        this.optimizationVisitors = optimizationVisitors;
    }

    @Override
    public Expression visit(BinaryExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(ConditionalExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(UnaryExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(ValueExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(MethodInvocationExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(FunctionInvocationExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(ContextVariableAccessExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(TernaryExpression s) {
        return visitAll(super.visit(s));
    }

    @Override
    public Expression visit(VariableMapAccessExpression s) {
        return visitAll(super.visit(s));
    }

    private Expression visitAll(Expression e) {
        for (OptimizationVisitor visitor : optimizationVisitors) {
            e = e.accept(visitor);
        }
        return e;
    }
}
