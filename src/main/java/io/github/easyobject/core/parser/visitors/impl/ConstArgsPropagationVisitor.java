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
import io.github.easyobject.core.parser.ast.ValueExpression;
import io.github.easyobject.core.parser.ast.VariableMapAccessExpression;
import io.github.easyobject.core.parser.ast.Variables;
import io.github.easyobject.core.parser.exception.impl.ScalarValueExpectedException;
import io.github.easyobject.core.parser.visitors.AbstractOptimizationVisitor;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.ScalarValue;

import java.util.List;

/**
 * AST nodes visitor that replaces variable references with actual values, if they are known on the compile time.
 */
public class ConstArgsPropagationVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ConstArgsPropagationVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(VariableMapAccessExpression expression) {
        Expression firstKey = expression.getKeys().get(0);
        if (firstKey instanceof ValueExpression) {
            Value<?> firstKeyValue = ((ValueExpression) firstKey).getValue();
            if (!(firstKeyValue instanceof ScalarValue)) {
                throw new ScalarValueExpectedException(firstKeyValue);
            }
            Value<?> variable = variables.getNullableConst(((ScalarValue<?>) firstKeyValue).toStringValue());
            if (variable == null) {
                return expression;
            }
            List<Expression> keys = expression.getKeys();
            for (int i = 1; i < keys.size(); i++) {
                if (!(keys instanceof ValueExpression)) {
                    // TODO replace first key with actual value
                    return expression;
                }
            }
            return new ValueExpression(expression.eval(variables));
        }
        return expression;
    }
}
