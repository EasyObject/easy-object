package den.vor.easy.object.value;

public abstract class NumberValue<T extends Number & Comparable<T>> extends ComparableValue<T> {

    @SuppressWarnings("unchecked")
    protected int compareTo(Value<?> value) {
        Object second = value.getValue();
        if (!(second instanceof Number)) {
            throw new UnsupportedOperationException();
        }
        if (getValue().getClass().isInstance(second)) {
            return getValue().compareTo((T) second);
        }
        Double secondDouble = ((Number) second).doubleValue();
        Double thisDouble = getValue().doubleValue();
        return thisDouble.compareTo(secondDouble);
    }
}
