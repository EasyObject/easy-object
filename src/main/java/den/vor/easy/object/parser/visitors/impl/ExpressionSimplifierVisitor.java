/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors.impl;

import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.TernaryExpression;
import den.vor.easy.object.parser.ast.Variables;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import den.vor.easy.object.value.Value;

/**
 * AST nodes visitor that evaluates nodes that can be evaluated on the compile time.
 */
public class ExpressionSimplifierVisitor extends AbstractOptimizationVisitor {

    private Variables variables;

    public ExpressionSimplifierVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(TernaryExpression expression) {
        if (isValue(expression.getCondition())) {
            Value<?> value = expression.getCondition().eval(variables);
            return value.as(Boolean.class) ? expression.getThenExpression() : expression.getElseExpression();
        }
        return expression;
    }
}
