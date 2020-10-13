package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.CompoundFactory;
import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.MapValue;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.StringValue;
import den.vor.easy.object.value.Value;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory extends CompoundFactory<Map<String, Object>> {

    private final Map<StringValue, Factory<?>> factories = new HashMap<>();

    public ObjectFactory and(String key, Factory<?> factory) {
        factories.put(StringValue.of(key), factory);
        return this;
    }

    @Override
    protected Map<? extends ScalarValue<?>, Factory<?>> getChildFactories() {
        return factories;
    }

    @Override
    protected Generator<Map<String, Object>> doGetGenerator(List<ScalarValue<?>> orderedKeys) {
        Map<ScalarValue<?>, Generator<?>> generators = orderedKeys.stream()
                .collect(toMap(Function.identity(), key -> getByKey(key).getGenerator(), (a, b) -> {
                    throw new IllegalArgumentException();
                }, LinkedHashMap::new));
        return context -> {
            HashMap<String, Object> result = new HashMap<>();
            generators.forEach((key, gen) -> result.put(key.toStringValue().getValue(), gen.getNext(context)));
            return result;
        };
    }

    private Factory<?> getByKey(ScalarValue<?> key) {
        Factory<?> factory = factories.get(key.toStringValue());
        if (factory == null) {
            throw new IllegalArgumentException();
        }
        return factory;
    }
}
