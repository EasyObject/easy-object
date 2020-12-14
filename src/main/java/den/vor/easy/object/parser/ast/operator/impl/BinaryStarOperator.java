package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.parser.ast.operator.BinaryOperator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

public class BinaryStarOperator extends BinaryOperator {

    @Override
    public Value<?> evaluate(Value<?> first, Value<?> second) {
        Object firstValue = first.getValue();
        Object secondValue = second.getValue();

        if (isString(firstValue)) {
            if (isInt(secondValue)) {
                return StringValue.of(multiplyString(firstValue.toString(), (int)secondValue));
            }
            throw new UnsupportedOperationException();
        }
        if (isInt(firstValue) && isInt(secondValue)) {
            int firstDouble = (int) firstValue;
            int secondDouble = (int) secondValue;
            return IntValue.of(firstDouble * secondDouble);
        }
        if (isNumber(firstValue) && isNumber(secondValue)) {
            double firstDouble = ((Number) firstValue).doubleValue();
            double secondDouble = ((Number) secondValue).doubleValue();
            return DoubleValue.of(firstDouble * secondDouble);
        }
        throw new UnsupportedOperationException();
    }

    private String multiplyString(String source, int count) {
        if (count < 0) {
            throw new UnsupportedOperationException();
        }
        return source.repeat(count);
    }
}

