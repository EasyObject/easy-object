package den.vor.easy.object.value;

public class StringValue extends ScalarValue<String> {

    private String value;

    public static StringValue of(String value) {
        return new StringValue(value);
    }

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return this;
    }

    @Override
    public String toString() {
        return "StringValue{" + value + '}';
    }
}
