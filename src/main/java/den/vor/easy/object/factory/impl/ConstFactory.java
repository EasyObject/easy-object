package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;

public class ConstFactory<T> extends Factory<T> {

    private final T value;

    public ConstFactory(T value) {
        this.value = value;
    }

    @Override
    public Generator<T> getGenerator() {
        return context -> value;
    }
}
