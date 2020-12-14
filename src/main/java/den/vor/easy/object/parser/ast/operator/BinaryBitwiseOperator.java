package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;
import den.vor.easy.object.value.impl.IntValue;

public abstract class BinaryBitwiseOperator extends BinaryOperator {

    public Value<?> evaluate(Value<?> first, Value<?> second) {
        Object firstValue = first.getValue();
        Object secondValue = second.getValue();
        if (isInt(firstValue) && isInt(secondValue)) {
            return IntValue.of(doEvaluate((int)firstValue, (int)secondValue));
        }
        throw new UnsupportedOperationException();
    }

    protected abstract int doEvaluate(int first, int second);

    public static class BitwiseOrOperator extends BinaryBitwiseOperator {

        @Override
        protected int doEvaluate(int first, int second) {
            return first | second;
        }
    }

    public static class BitwiseAndOperator extends BinaryBitwiseOperator {

        @Override
        protected int doEvaluate(int first, int second) {
            return first & second;
        }
    }

    public static class BitwiseXorOperator extends BinaryBitwiseOperator {

        @Override
        protected int doEvaluate(int first, int second) {
            return first ^ second;
        }
    }
}
