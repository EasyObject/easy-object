package den.vor.easy.object.consumer.formatter;

import den.vor.easy.object.value.Value;

import java.util.List;

/**
 * Formatter for generated values. Basically transforms one or multiple {@link Value} into a new object
 * @param <T> return type
 */
public interface Formatter<T> {

    T format(List<Value<?>> value);

    T format(Value<?> value);
}
