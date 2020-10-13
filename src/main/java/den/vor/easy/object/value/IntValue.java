package den.vor.easy.object.value;

public class IntValue extends ScalarValue<Integer> {

    private final int value;

    public static IntValue of(int value) {
        return new IntValue(value);
    }

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return StringValue.of(String.valueOf(value));
    }

    @Override
    public String toString() {
        return "IntValue{" + value + '}';
    }
}
