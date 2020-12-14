package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;

public abstract class BinaryComparisonOperator extends BinaryOperator {

    @Override
    public Value<?> evaluate(Value<?> first, Value<?> second) {
        Object firstValue = first.getValue();
        Object secondValue = second.getValue();
        if (isInt(firstValue) && isInt(secondValue)) {
            return BooleanValue.of(test((int)firstValue, (int)secondValue));
        }
        if (isNumber(firstValue) && isNumber(secondValue)) {
            Number firstNumber = (Number) firstValue;
            Number secondNumber = (Number) secondValue;
            return BooleanValue.of(test(firstNumber.doubleValue(), secondNumber.doubleValue()));
        }
        if (isString(firstValue) && isString(secondValue)) {
            return BooleanValue.of(test((String) firstValue, (String)secondValue));
        }
        throw new UnsupportedOperationException();
    }

    protected abstract boolean test(double first, double second);

    protected abstract boolean test(int first, int second);

    protected abstract boolean test(String first, String second);

    public static class BinaryGtOperator extends BinaryComparisonOperator {

        @Override
        protected boolean test(double first, double second) {
            return first > second;
        }

        @Override
        protected boolean test(int first, int second) {
            return first > second;
        }

        @Override
        protected boolean test(String first, String second) {
            return first.compareTo(second) > 0;
        }
    }

    public static class BinaryGteOperator extends BinaryComparisonOperator {

        @Override
        protected boolean test(double first, double second) {
            return first >= second;
        }

        @Override
        protected boolean test(int first, int second) {
            return first >= second;
        }

        @Override
        protected boolean test(String first, String second) {
            return first.compareTo(second) >= 0;
        }
    }

    public static class BinaryLtOperator extends BinaryComparisonOperator {

        @Override
        protected boolean test(double first, double second) {
            return first < second;
        }

        @Override
        protected boolean test(int first, int second) {
            return first < second;
        }

        @Override
        protected boolean test(String first, String second) {
            return first.compareTo(second) < 0;
        }
    }

    public static class BinaryLteOperator extends BinaryComparisonOperator {

        @Override
        protected boolean test(double first, double second) {
            return first <= second;
        }

        @Override
        protected boolean test(int first, int second) {
            return first <= second;
        }

        @Override
        protected boolean test(String first, String second) {
            return first.compareTo(second) <= 0;
        }
    }
}
