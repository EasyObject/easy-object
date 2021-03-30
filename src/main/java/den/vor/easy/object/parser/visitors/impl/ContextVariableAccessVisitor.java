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
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.StringValue;

import java.util.ArrayList;
import java.util.List;

import static den.vor.easy.object.parser.ExpressionConstants.PARENT_REF;
import static den.vor.easy.object.parser.ExpressionConstants.THIS_REF;

/**
 * Visitor that replaces {@link VariableMapAccessExpression} with {@link ContextVariableAccessExpression} if possible.
 */
public class ContextVariableAccessVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ContextVariableAccessVisitor(Variables variables) {
        this.variables = variables;
    }

    /**
     * If all keys of {@link VariableMapAccessExpression} are instance of {@link ValueExpression},
     * evaluates them and creates {@link ContextVariableAccessExpression} for better performance.
     * @param expression expression to visit
     * @return original or optimized expression
     */
    @Override
    public Expression visit(VariableMapAccessExpression expression) {
        List<Expression> keys = expression.getKeys();
        if (keys.stream().allMatch(ValueExpression.class::isInstance)) {
            return getContextVariableAccessExpression(keys);
        }
        return expression;
    }

    private ContextVariableAccessExpression getContextVariableAccessExpression(List<Expression> keys) {
        int parentHops = 0;
        List<ScalarValue<?>> keyHops = new ArrayList<>();

        int processed = 0;
        boolean parentEscaped = false;
        boolean thisEscaped = false;

        for (; processed < keys.size(); processed++) {
            Value<?> value = keys.get(processed).eval(variables);
            if (!(value instanceof ScalarValue<?>)) {
                throw new RuntimeException();
            }
            ScalarValue<?> scalarValue = (ScalarValue<?>) value;
            if (!(scalarValue instanceof StringValue)) {
                parentEscaped = true;
                thisEscaped = true;
                keyHops.add(scalarValue);
            } else {
                StringValue stringValue = (StringValue) scalarValue;
                String string = stringValue.getValue();
                if (!thisEscaped && THIS_REF.equals(string)) {
                    thisEscaped = true;
                    parentEscaped = true;
                } else if (!parentEscaped && PARENT_REF.equals(string)) {
                    parentHops++;
                    thisEscaped = true;
                } else {
                    keyHops.add(stringValue);
                    thisEscaped = true;
                    parentEscaped = true;
                }
            }
        }
        return new ContextVariableAccessExpression(parentHops, keyHops);
    }
}
