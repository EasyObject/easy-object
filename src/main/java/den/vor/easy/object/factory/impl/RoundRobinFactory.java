package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory that returns elements specified by {@link RoundRobinFactory#values} field one by one.
 * @param <T> type of value returned
 */
public class RoundRobinFactory<T> extends Factory<T, Value<T>> {

    private final List<? extends Value<T>> values;

    public RoundRobinFactory(List<? extends Value<T>> values) {
        this.values = values;
    }

    public RoundRobinFactory(Value<T>... values) {
        this.values = Arrays.asList(values);
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        AtomicInteger index = new AtomicInteger();
        return context -> {
            int nextIndex = index.getAndUpdate(i -> (i + 1) % values.size());
            return values.get(nextIndex);
        };
    }
}
