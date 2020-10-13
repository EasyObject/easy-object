package den.vor.easy.object.value;

import java.util.Collections;
import java.util.Map;

public class MapValue extends CompoundValue<Map<ScalarValue<?>, Value<?>>> {

    private static final MapValue EMPTY_MAP_VALUE = new MapValue(Collections.emptyMap());

    public static MapValue emptyMap() {
        return EMPTY_MAP_VALUE;
    }

    private final Map<ScalarValue<?>, Value<?>> map;

    public MapValue(Map<ScalarValue<?>, Value<?>> map) {
        this.map = map;
    }

    @Override
    public Map<ScalarValue<?>, Value<?>> getValue() {
        return map;
    }

    public void put(ScalarValue<?> key, Value<?> value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        return "MapValue{" + map + '}';
    }
}
