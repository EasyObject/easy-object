package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

/**
 * Factory, which {@link Generator} always returns the same value
 * @param <T> type of value
 */
public class ConstFactory<T> extends Factory<T, Value<T>> {

    private final Value<T> value;

    /**
     * Creates new factory instance
     * @param value value to return
     */
    public ConstFactory(Value<T> value) {
        this.value = value;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        return context -> value;
    }
}
