package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.parser.ast.operator.UnaryOperator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;

public class UnaryMinusOperator extends UnaryOperator {

    @Override
    public Value<?> evaluate(Value<?> value) {
        Object object = value.getValue();
        if (isInt(object)) {
            return IntValue.of(-((int)object));
        }
        if (isDouble(object)) {
            return DoubleValue.of(-((double)object));
        }
        throw new UnsupportedOperationException();
    }
}
