package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.parser.ast.operator.UnaryOperator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;

public class UnaryNotOperator extends UnaryOperator {

    @Override
    public Value<?> evaluate(Value<?> value) {
        Object object = value.getValue();
        if (isBool(object)) {
            boolean bool = (boolean) object;
            return BooleanValue.of(!bool);
        }
        throw new UnsupportedOperationException();
    }
}
