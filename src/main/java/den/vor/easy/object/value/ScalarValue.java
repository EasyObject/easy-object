package den.vor.easy.object.value;

import den.vor.easy.object.value.impl.StringValue;

public abstract class ScalarValue<T> extends Value<T> {

    public StringValue toStringValue() {
        throw new UnsupportedOperationException();
    }
}