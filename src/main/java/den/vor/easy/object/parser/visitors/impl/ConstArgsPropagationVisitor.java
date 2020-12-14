package den.vor.easy.object.parser.visitors.impl;

import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.ValueExpression;
import den.vor.easy.object.parser.ast.VariableMapAccessExpression;
import den.vor.easy.object.parser.ast.Variables;
import den.vor.easy.object.parser.visitors.AbstractOptimizationVisitor;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.util.List;

public class ConstArgsPropagationVisitor extends AbstractOptimizationVisitor {

    private final Variables variables;

    public ConstArgsPropagationVisitor(Variables variables) {
        this.variables = variables;
    }

    @Override
    public Expression visit(VariableMapAccessExpression s) {
        Expression firstKey = s.getKeys().get(0);
        if (firstKey instanceof ValueExpression) {
            Value<?> firstKeyValue = ((ValueExpression) firstKey).getValue();
            if (!(firstKeyValue instanceof ScalarValue)) {
                throw new RuntimeException();
            }
            Value<?> variable = variables.getNullableVariable(((ScalarValue<?>) firstKeyValue).toStringValue());
            if (variable == null) {
                return s;
            }
            List<Expression> keys = s.getKeys();
            for (int i = 1; i < keys.size(); i++) {
                if (!(keys instanceof ValueExpression)) {
                    // TODO replace first key with actual value
                    return s;
                }
            }
            return new ValueExpression(s.eval(variables));
        }
        return s;
    }
}
