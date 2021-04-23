/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors.impl;

import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.ast.TernaryExpression;
import io.github.easyobject.core.parser.ast.Variables;
import io.github.easyobject.core.parser.visitors.AbstractOptimizationVisitor;
import io.github.easyobject.core.value.Value;

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
