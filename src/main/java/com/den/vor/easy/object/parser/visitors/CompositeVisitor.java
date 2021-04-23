/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors;

import com.den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.ast.*;

import java.util.List;

/**
 * AST nodes visitor that contains a collection of visitors and applies them one-by-one.
 */
public class CompositeVisitor extends DelegatingOptimizationVisitor {

    private final List<AbstractOptimizationVisitor> optimizationVisitors;

    public CompositeVisitor(List<AbstractOptimizationVisitor> optimizationVisitors) {
        this.optimizationVisitors = optimizationVisitors;
    }

    @Override
    public Expression visit(BinaryExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(ConditionalExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(UnaryExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(ValueExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(MethodInvocationExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(FunctionInvocationExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(ContextVariableAccessExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(TernaryExpression expression) {
        return visitAll(super.visit(expression));
    }

    @Override
    public Expression visit(VariableMapAccessExpression expression) {
        return visitAll(super.visit(expression));
    }

    private Expression visitAll(Expression e) {
        for (OptimizationVisitor visitor : optimizationVisitors) {
            e = e.accept(visitor);
        }
        return e;
    }
}
