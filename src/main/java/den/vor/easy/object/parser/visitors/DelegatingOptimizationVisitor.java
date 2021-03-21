/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors;

import den.vor.easy.object.parser.ast.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class DelegatingOptimizationVisitor extends AbstractOptimizationVisitor {

    @Override
    public Expression visit(BinaryExpression expression) {
        Expression leftVisited = expression.getLeft().accept(this);
        Expression rightVisited = expression.getRight().accept(this);
        if (leftVisited != expression.getLeft() || rightVisited != expression.getRight()) {
            return new BinaryExpression(leftVisited, rightVisited, expression.getOperator());
        }
        return expression;
    }

    @Override
    public Expression visit(ConditionalExpression expression) {
        Expression leftVisited = expression.getLeft().accept(this);
        Expression rightVisited = expression.getRight().accept(this);
        if (leftVisited != expression.getLeft() || rightVisited != expression.getRight()) {
            return new ConditionalExpression(leftVisited, rightVisited, expression.getOperator());
        }
        return expression;
    }

    @Override
    public Expression visit(UnaryExpression expression) {
        Expression visitedExpression = expression.getExpression().accept(this);
        if (visitedExpression != expression.getExpression()) {
            return new UnaryExpression(visitedExpression, expression.getOperation());
        }
        return expression;
    }

    @Override
    public Expression visit(TernaryExpression expression) {
        Expression condition = expression.getCondition().accept(this);
        Expression thenExpression = expression.getThenExpression().accept(this);
        Expression elseExpression = expression.getElseExpression().accept(this);
        if (condition != expression.getCondition() ||
                thenExpression != expression.getThenExpression() ||
                elseExpression != expression.getElseExpression()) {
            return new TernaryExpression(condition, thenExpression, elseExpression);
        }
        return expression;
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
    public Expression visit(VariableMapAccessExpression expression) {
        List<Expression> keys = expression.getKeys();
        List<Expression> visitedKeys = keys.stream().map(a -> a.accept(this)).collect(Collectors.toList());
        for (int i = 0; i < visitedKeys.size(); i++) {
            if (keys.get(i) != visitedKeys.get(i)) {
                return new VariableMapAccessExpression(visitedKeys);
            }
        }
        return expression;
    }
}
