package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.CompoundValue;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapValue extends CompoundValue<Map<ScalarValue<?>, Value<?>>> {

    private static final MapValue EMPTY_MAP_VALUE = new MapValue(Collections.emptyMap());

    public static MapValue emptyMap() {
        return EMPTY_MAP_VALUE;
    }

    private static final Map<String, FunctionalValue<Map<ScalarValue<?>, Value<?>>>> METHODS = Map.of(
            "size", new FunctionalValue<>((map, args) -> IntValue.of(map.getValue().size()))
            );

    private final Map<ScalarValue<?>, Value<?>> map;

    public MapValue(Map<ScalarValue<?>, Value<?>> map) {
        this.map = map;
    }

    public MapValue() {
        this(new HashMap<>());
    }

    @Override
    public Map<ScalarValue<?>, Value<?>> getValue() {
        return map;
    }

    @Override
    public Value<?> get(ScalarValue<?> key) {
        return map.get(key);
    }

    @Override
    protected Map<String, FunctionalValue<Map<ScalarValue<?>, Value<?>>>> getMethods() {
        return METHODS;
    }

    public void put(ScalarValue<?> key, Value<?> value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        return "MapValue{" + map + '}';
    }
}
