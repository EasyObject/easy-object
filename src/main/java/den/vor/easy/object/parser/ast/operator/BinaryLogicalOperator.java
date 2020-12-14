package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;

public abstract class BinaryLogicalOperator extends BinaryOperator {

    public Value<?> evaluate(Value<?> first, Value<?> second) {
        Object firstValue = first.getValue();
        Object secondValue = second.getValue();
        if (isBool(firstValue) && isBool(secondValue)) {
            return BooleanValue.of(doEvaluate((boolean)firstValue, (boolean)secondValue));
        }
        throw new UnsupportedOperationException();
    }

    protected abstract boolean doEvaluate(boolean first, boolean second);

    public static class LogicalOrOperator extends BinaryLogicalOperator {

        @Override
        protected boolean doEvaluate(boolean first, boolean second) {
            return first || second;
        }
    }

    public static class LogicalAndOperator extends BinaryLogicalOperator {

        @Override
        protected boolean doEvaluate(boolean first, boolean second) {
            return first && second;
        }
    }

    public static class LogicalXorOperator extends BinaryLogicalOperator {

        @Override
        protected boolean doEvaluate(boolean first, boolean second) {
            return first ^ second;
        }
    }
}
