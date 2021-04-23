/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.impl;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.CompoundValue;
import com.den.vor.easy.object.value.ScalarValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Value that encapsulates {@link Map}.
 * Does not support any arithmetical or comparison operations.
 */
public class MapValue extends CompoundValue<Map<ScalarValue<?>, Value<?>>> {

    private static final MapValue EMPTY_MAP_VALUE = new MapValue(Collections.emptyMap());
    private static final Map<String, FunctionalValue<Map<ScalarValue<?>, Value<?>>>> METHODS = Map.of(
            "size", new FunctionalValue<>((m, args) -> IntValue.of(m.getValue().size()))
    );

    private final Map<ScalarValue<?>, Value<?>> map;

    public MapValue(Map<ScalarValue<?>, Value<?>> map) {
        this.map = map;
    }

    public MapValue() {
        this(new HashMap<>());
    }

    /**
     * Returns an empty read-only map. If you need read-write map, see {@link MapValue#of()}.
     * @return map value instance
     */
    public static MapValue emptyMap() {
        return EMPTY_MAP_VALUE;
    }

    /**
     * Wraps a given map.
     * @return map value instance
     */
    public static MapValue of(Map<ScalarValue<?>, Value<?>> value) {
        return new MapValue(value);
    }

    /**
     * Wraps a given map.
     * @return map value instance
     */
    public static MapValue of() {
        return of(new HashMap<>());
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

    public void remove(ScalarValue<?> key) {
        map.remove(key);
    }

    @Override
    public String toString() {
        return "MapValue{" + map + '}';
    }
}
