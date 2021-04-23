/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors.impl;

import com.den.vor.easy.object.parser.ast.Expression;
import com.den.vor.easy.object.parser.ast.TernaryExpression;
import com.den.vor.easy.object.parser.ast.Variables;
import com.den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import com.den.vor.easy.object.value.Value;

/**
 * AST nodes visitor that evaluates nodes that can be evaluated on the compile time.
 */
public class ExpressionSimplifierVisitor extends AbstractOptimizationVisitor {

    private Variables variables;

    public ExpressionSimplifierVisitor(Variables variables) {
        this.variables = variables;
    }

    /**
     * If ternary expression's condition can be evaluated on the compile time,
     * evaluates it and replaces with expression with one of it's branches, depending on evaluation result.
     * @param expression expression to visit
     * @return original or optimized expression
     */
    @Override
    public Expression visit(TernaryExpression expression) {
        Expression condition = expression.getCondition();
        if (isValue(condition)) {
            Value<?> value = condition.eval(variables);
            Boolean result = value.as(Boolean.class);
            if (Boolean.TRUE.equals(result)) {
                return expression.getThenExpression();
            } else {
                return expression.getElseExpression();
            }
        }
        return expression;
    }
}
