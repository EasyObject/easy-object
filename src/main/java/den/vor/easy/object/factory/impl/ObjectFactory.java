package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.CompoundFactory;
import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory extends CompoundFactory<Map<ScalarValue<?>, Value<?>>, MapValue> {

    private final Map<StringValue, Factory<?, ?>> factories = new HashMap<>();

    public ObjectFactory and(String key, Factory<?, ?> factory) {
        factories.put(StringValue.of(key), factory);
        return this;
    }

    public ObjectFactory and(String key, Value<?> value) {
        return and(key, new ConstFactory<>(value));
    }

    @Override
    protected Map<? extends ScalarValue<?>, Factory<?, ?>> getChildFactories() {
        return factories;
    }

    @Override
    protected Generator<MapValue> doGetGenerator(List<ScalarValue<?>> orderedKeys) {
        Map<ScalarValue<?>, Generator<? extends Value<?>>> generators = orderedKeys.stream()
                .collect(toMap(Function.identity(), key -> getByKey(key).getGenerator(), (a, b) -> {
                    throw new IllegalArgumentException();
                }, LinkedHashMap::new));
        return context -> {
            MapValue mapValue = new MapValue();
            mapValue.setParent(context.getParent());

            GenerationContext generationContext = GenerationContext.fromParent(context)
                    .setContext(mapValue)
                    .setParent(mapValue);
            for (Map.Entry<ScalarValue<?>, Generator<? extends Value<?>>> entry : generators.entrySet()) {
                StringValue key = entry.getKey().toStringValue();
                Value<?> value = entry.getValue().getNext(generationContext);
                value.setParent(mapValue);
                mapValue.put(key, value);
            }
            return mapValue;
        };
    }

    private Factory<?, ?> getByKey(ScalarValue<?> key) {
        Factory<?, ?> factory = factories.get(key.toStringValue());
        if (factory == null) {
            throw new IllegalArgumentException();
        }
        return factory;
    }
}
