package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DelegatingOptimizationVisitor extends AbstractOptimizationVisitor {

    @Override
    public Expression visit(BinaryExpression s) {
        Expression leftVisited = s.getLeft().accept(this);
        Expression rightVisited = s.getRight().accept(this);
        if (leftVisited != s.getLeft() || rightVisited != s.getRight()) {
            return new BinaryExpression(leftVisited, rightVisited, s.getOperation());
        }
        return s;
    }

    @Override
    public Expression visit(ConditionalExpression s) {
        Expression leftVisited = s.getLeft().accept(this);
        Expression rightVisited = s.getRight().accept(this);
        if (leftVisited != s.getLeft() || rightVisited != s.getRight()) {
            return new ConditionalExpression(leftVisited, rightVisited, s.getOperation());
        }
        return s;
    }

    @Override
    public Expression visit(UnaryExpression s) {
        Expression visitedExpression = s.getExpression().accept(this);
        if (visitedExpression != s.getExpression()) {
            return new UnaryExpression(visitedExpression, s.getOperation());
        }
        return s;
    }

    @Override
    public Expression visit(TernaryExpression s) {
        Expression condition = s.getCondition().accept(this);
        Expression thenExpression = s.getThenExpression().accept(this);
        Expression elseExpression = s.getElseExpression().accept(this);
        if (condition != s.getCondition() ||
                thenExpression != s.getThenExpression() ||
                elseExpression != s.getElseExpression()) {
            return new TernaryExpression(condition, thenExpression, elseExpression);
        }
        return s;
    }

    @Override
    public Expression visit(MethodInvocationExpression s) {
        Expression expression = s.getExpression().accept(this);
        Expression method = s.getMethod().accept(this);
        List<Expression> arguments = s.getArgs().stream().map(a -> a.accept(this)).collect(Collectors.toList());
        if (expression != s.getExpression() || method != s.getMethod()) {
            return new MethodInvocationExpression(expression, method, arguments);
        }
        for (int i = 0; i < arguments.size(); i++) {
            if (s.getArgs().get(i) != arguments.get(i)) {
                return new MethodInvocationExpression(expression, method, arguments);
            }
        }
        return s;
    }

    @Override
    public Expression visit(FunctionInvocationExpression s) {
        Expression expression = s.getExpression().accept(this);
        List<Expression> arguments = s.getArgs().stream().map(a -> a.accept(this)).collect(Collectors.toList());
        if (expression != s.getExpression()) {
            return new FunctionInvocationExpression(expression, arguments);
        }
        for (int i = 0; i < arguments.size(); i++) {
            if (s.getArgs().get(i) != arguments.get(i)) {
                return new FunctionInvocationExpression(expression, arguments);
            }
        }
        return s;
    }

    @Override
    public Expression visit(VariableMapAccessExpression s) {
        List<Expression> keys = s.getKeys();
        List<Expression> visitedKeys = keys.stream().map(a -> a.accept(this)).collect(Collectors.toList());
        for (int i = 0; i < visitedKeys.size(); i++) {
            if (keys.get(i) != visitedKeys.get(i)) {
                return new VariableMapAccessExpression(visitedKeys);
            }
        }
        return s;
    }
}
