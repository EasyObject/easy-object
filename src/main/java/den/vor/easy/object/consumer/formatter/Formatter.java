package den.vor.easy.object.consumer.formatter;

import den.vor.easy.object.value.Value;

import java.util.List;

public interface Formatter<T> {

    T format(List<Value<?>> value);

    T format(Value<?> value);
}
