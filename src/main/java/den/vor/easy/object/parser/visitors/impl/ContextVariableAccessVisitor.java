package den.vor.easy.object.parser.visitors.impl;


import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.StringValue;

import java.util.ArrayList;
import java.util.List;

public class ContextVariableAccessVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ContextVariableAccessVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(VariableMapAccessExpression s) {
        Expression expression = super.visit(s);

        if (expression instanceof VariableMapAccessExpression) {
            VariableMapAccessExpression variableMapAccessExpression = (VariableMapAccessExpression) expression;
            List<Expression> keys = variableMapAccessExpression.getKeys();
            if (keys.stream().allMatch(k -> k instanceof ValueExpression)) {
                return getContextVariableAccessExpression(keys);
            }
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
                if (!thisEscaped && "this".equals(string)) {
                    thisEscaped = true;
                    parentEscaped = true;
                } else if (!parentEscaped && "parent".equals(string)) {
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
