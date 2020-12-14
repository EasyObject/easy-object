package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.parser.ast.operator.UnaryOperator;
import den.vor.easy.object.value.Value;

public class UnaryPlusOperator extends UnaryOperator {

    @Override
    public Value<?> evaluate(Value<?> value) {
        Object object = value.getValue();
        if (isNumber(object)) {
            return value;
        }
        throw new UnsupportedOperationException();
    }
}
