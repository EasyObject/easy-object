package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.stream.Collectors;

public class ListValue<T> extends Value<List<T>> {

    private final List<Value<T>> values;
    private List<T> unwrappedValues;

    public ListValue(List<Value<T>> values) {
        this.values = values;
    }

    @Override
    public List<T> getValue() {
        if (unwrappedValues == null) {
            unwrappedValues = values.stream().map(Value::getValue).collect(Collectors.toList());
        }
        return unwrappedValues;
    }

    @Override
    public Value<?> get(ScalarValue<?> key) {
        if (key instanceof IntValue) {
            IntValue intValue = (IntValue) key;
            return values.get(intValue.getValue());
        }
        return super.get(key);
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return "ListValue{" + values + '}';
    }
}
