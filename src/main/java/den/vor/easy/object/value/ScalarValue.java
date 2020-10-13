package den.vor.easy.object.value;

public abstract class ScalarValue<T> extends Value<T> {

    public StringValue toStringValue() {
        throw new UnsupportedOperationException();
    }
}
