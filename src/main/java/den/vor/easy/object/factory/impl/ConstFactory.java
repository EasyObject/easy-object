package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

public class ConstFactory<T> extends Factory<T, Value<T>> {

    private final Value<T> value;

    public ConstFactory(Value<T> value) {
        this.value = value;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        return context -> value;
    }
}
