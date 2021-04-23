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

/**
 * Skeletal implementation of {@link OptimizationVisitor}.
 * Just returns original expressions.
 */
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
