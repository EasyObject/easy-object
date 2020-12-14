package den.vor.easy.object.parser.visitors.impl;


import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;

public class ConstantFoldingVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ConstantFoldingVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(UnaryExpression unaryExpression) {
        unaryExpression = (UnaryExpression) super.visit(unaryExpression);
        if (isValue(unaryExpression.getExpression())) {
            return new ValueExpression(unaryExpression.eval(variables));
        }
        return super.visit(unaryExpression);
    }

    @Override
    public Expression visit(BinaryExpression s) {
        Expression visited = super.visit(s);
        if (!(visited instanceof BinaryExpression)) {
            return visited;
        }
        s = (BinaryExpression) visited;
        if (isValue(s.getLeft()) && isValue(s.getRight())) {
            return new ValueExpression(s.eval(variables));
        }
        return super.visit(s);
    }

    @Override
    public Expression visit(ConditionalExpression s) {
        s = (ConditionalExpression) super.visit(s);
        if (isValue(s.getLeft()) && isValue(s.getRight())) {
            return new ValueExpression(s.eval(variables));
        }
        return super.visit(s);
    }
}
