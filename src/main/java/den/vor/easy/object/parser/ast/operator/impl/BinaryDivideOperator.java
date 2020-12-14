package den.vor.easy.object.parser.ast.operator.impl;

import den.vor.easy.object.parser.ast.operator.BinaryOperator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;

public class BinaryDivideOperator extends BinaryOperator {

    @Override
    public Value<?> evaluate(Value<?> first, Value<?> second) {
        Object firstValue = first.getValue();
        Object secondValue = second.getValue();

        if (isInt(firstValue) && isInt(secondValue)) {
            int firstDouble = (int) firstValue;
            int secondDouble = (int) secondValue;
            return IntValue.of(firstDouble / secondDouble);
        }
        if (isNumber(firstValue) && isNumber(secondValue)) {
            double firstDouble = ((Number) firstValue).doubleValue();
            double secondDouble = ((Number) secondValue).doubleValue();
            return DoubleValue.of(firstDouble / secondDouble);
        }
        throw new UnsupportedOperationException();
    }
}
