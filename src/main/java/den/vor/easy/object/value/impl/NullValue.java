package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

public class NullValue extends Value<Object> {

    public static final NullValue NULL = new NullValue();

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "NullValue{}";
    }
}
