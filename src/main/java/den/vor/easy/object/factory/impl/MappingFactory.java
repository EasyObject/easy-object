package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.function.Function;

public class MappingFactory<T, R, P extends Value<R>> extends Factory<T, Value<T>> {

    private Factory<R, P> factory;
    private Function<P, ? extends Value<T>> mappingFunction;

    public MappingFactory(Factory<R, P> factory,
                          Function<P, ? extends Value<T>> mappingFunction) {
        this.factory = factory;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        Generator<P> generator = factory.getGenerator();
        return context -> mappingFunction.apply(generator.getNext(context));
    }

    @Override
    public List<FieldRef> getDependencies() {
        return factory.getDependencies();
    }
}
