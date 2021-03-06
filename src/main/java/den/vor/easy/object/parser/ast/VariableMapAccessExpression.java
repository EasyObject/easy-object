package den.vor.easy.object.parser.ast;


import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.StringValue;

import java.util.List;

import static den.vor.easy.object.parser.ExpressionConstants.PARENT_REF;
import static den.vor.easy.object.parser.ExpressionConstants.THIS_REF;

public class VariableMapAccessExpression implements Expression {

    private final List<Expression> keys;

    public VariableMapAccessExpression(List<Expression> keys) {
        this.keys = keys;
    }

    @Override
    public Value<?> eval(Variables variables) {
        int processed = 0;
        boolean parentEscaped = false;
        boolean thisEscaped = false;

        Value<?> result = null;
        for (; processed < keys.size(); processed++) {
            Value<?> value = keys.get(processed).eval(variables);
            if (!(value instanceof ScalarValue<?>)) {
                throw new RuntimeException();
            }
            ScalarValue<?> scalarValue = (ScalarValue<?>) value;
            if (!(scalarValue instanceof StringValue)) {
                parentEscaped = true;
                thisEscaped = true;
                if (result == null) {
                    throw new RuntimeException();
                }
                result = result.get(scalarValue);
            } else {
                StringValue stringValue = (StringValue) scalarValue;
                String string = stringValue.getValue();
                if (!thisEscaped && THIS_REF.equals(string)) {
                    result = variables.getContext();
                    thisEscaped = true;
                    parentEscaped = true;
                } else if (!parentEscaped && PARENT_REF.equals(string)) {
                    result = (result == null ? variables.getContext() : result).getParent();
                    thisEscaped = true;
                } else {
                    if (result == null) {
                        result = variables.getVariable(stringValue);
                    } else {
                        result = result.get(stringValue);
                    }
                    thisEscaped = true;
                    parentEscaped = true;
                }
            }
        }
        return result;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Expression> getKeys() {
        return keys;
    }
}
