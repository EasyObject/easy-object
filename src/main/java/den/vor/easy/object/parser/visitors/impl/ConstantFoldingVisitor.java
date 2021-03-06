/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors.impl;


import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;

/**
 * AST nodes visitor that folds constant expressions, if they can be calculated on the compile time.
 */
public class ConstantFoldingVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ConstantFoldingVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(UnaryExpression expression) {
        if (isValue(expression.getExpression())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    @Override
    public Expression visit(BinaryExpression expression) {
        if (isValue(expression.getLeft()) && isValue(expression.getRight())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }

    @Override
    public Expression visit(ConditionalExpression expression) {
        if (isValue(expression.getLeft()) && isValue(expression.getRight())) {
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }
}
