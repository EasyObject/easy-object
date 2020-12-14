package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

import java.util.function.Supplier;

public class SupplierFactory<T, R extends Value<T>> extends Factory<T, R> {

    private Supplier<R> supplier;

    public SupplierFactory(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Generator<R> getGenerator() {
        return context -> supplier.get();
    }
}
