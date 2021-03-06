/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            ScalarValue<?> scalarValue = getScalarValue(value);
            if (!(scalarValue instanceof StringValue)) {
                parentEscaped = true;
                thisEscaped = true;
                result = getByKeyOrThrow(result, scalarValue);
            } else {
                StringValue stringValue = (StringValue) scalarValue;
                String string = stringValue.getValue();
                if (shouldEscapeThis(thisEscaped, string)) {
                    result = variables.getContext();
                    thisEscaped = true;
                    parentEscaped = true;
                } else if (shouldEscapeParent(parentEscaped, string)) {
                    result = getResult(result, variables).getParent();
                    thisEscaped = true;
                } else {
                    result = getByKey(variables, result, stringValue);
                    thisEscaped = true;
                    parentEscaped = true;
                }
            }
        }
        return result;
    }

    private Value<?> getByKey(Variables variables, Value<?> result, StringValue stringValue) {
        if (result == null) {
            result = variables.getVariable(stringValue);
        } else {
            result = result.get(stringValue);
        }
        return result;
    }

    private Value<?> getResult(Value<?> result, Variables variables) {
        return result == null ? variables.getContext() : result;
    }

    private boolean shouldEscapeParent(boolean parentEscaped, String string) {
        return !parentEscaped && PARENT_REF.equals(string);
    }

    private boolean shouldEscapeThis(boolean thisEscaped, String string) {
        return !thisEscaped && THIS_REF.equals(string);
    }

    private Value<?> getByKeyOrThrow(Value<?> result, ScalarValue<?> scalarValue) {
        if (result == null) {
            throw new RuntimeException();
        }
        result = result.get(scalarValue);
        return result;
    }

    private ScalarValue<?> getScalarValue(Value<?> value) {
        if (!(value instanceof ScalarValue<?>)) {
            throw new RuntimeException();
        }
        return (ScalarValue<?>) value;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Expression> getKeys() {
        return keys;
    }
}
