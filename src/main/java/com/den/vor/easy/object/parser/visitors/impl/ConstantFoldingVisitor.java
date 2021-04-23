/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors.impl;


import com.den.vor.easy.object.parser.ast.*;
import com.den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;

/**
 * AST nodes visitor that folds constant expressions, if they can be calculated on the compile time.
 */
public class ConstantFoldingVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ConstantFoldingVisitor(Variables variables) {
        this.variables = variables;
    }

    /**
     * Folds an unary expression, if a nested expression is instance of {@link ValueExpression}.
     * Example: {@code -3} will be folded into a single value.
     * @param expression expression to visit
     * @return original or evaluated  expression or evaluated
     */
    @Override
    public Expression visit(UnaryExpression expression) {
        if (isValue(expression.getExpression())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    /**
     * Folds an binary expression, if a nested expressions are instances of {@link ValueExpression}.
     * Example: {@code 3 + 2} will be folded into a single value.
     * @param expression expression to visit
     * @return original or evaluated  expression or evaluated
     */
    @Override
    public Expression visit(BinaryExpression expression) {
        if (isValue(expression.getLeft()) && isValue(expression.getRight())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    /**
     * Folds an conditional expression, if a nested expressions are instances of {@link ValueExpression}.
     * Example: {@code 3 > 2} will be folded into a single value.
     * @param expression expression to visit
     * @return original or evaluated  expression or evaluated
     */
    @Override
    public Expression visit(ConditionalExpression expression) {
        if (isValue(expression.getLeft()) && isValue(expression.getRight())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }
}
