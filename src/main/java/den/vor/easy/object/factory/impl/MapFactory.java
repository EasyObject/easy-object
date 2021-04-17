/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.factory.LengthFactory;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory that generates a map. Size of map is controlled by {@link LengthFactory#getLengthFactory()},
 * keys generated - by {@link MapFactory#keyFactory}, values - {@link MapFactory#valueFactory}.
 *
 * If duplicate keys are generated, only the latest is preserved. Other will be discarded, making map smaller.
 *
 * @param <K> type of key
 * @param <V> type of value
 */
public class MapFactory<K, V> extends LengthFactory<Map<ScalarValue<?>, Value<?>>, MapValue> {

    private Factory<?, ? extends ScalarValue<K>> keyFactory;
    private Factory<?, ? extends Value<V>> valueFactory;

    public MapFactory(Factory<?, ? extends ScalarValue<K>> keyFactory,
                      Factory<?, ? extends Value<V>> valueFactory,
                      Factory<Integer, ? extends Value<Integer>> lengthFactory) {
        super(lengthFactory);
        this.keyFactory = keyFactory;
        this.valueFactory = valueFactory;
    }

    @Override
    public Generator<MapValue> getGenerator() {
        Generator<? extends ScalarValue<K>> keyGenerator = keyFactory.getGenerator();
        Generator<? extends Value<V>> valueGenerator = valueFactory.getGenerator();
        Generator<? extends Value<Integer>> lengthGenerator = getLengthFactory().getGenerator();
        return context -> {
            MapValue result = new MapValue();
            Integer length = lengthGenerator.getNext(context).getValue();
            for (int i = 0; i < length; i++) {
                result.put(keyGenerator.getNext(context), valueGenerator.getNext(context));
            }
            return result;
        };
    }

    @Override
    protected List<FieldRef> getNestedFactoriesDependencies() {
        List<FieldRef> dependencies = new ArrayList<>();
        dependencies.addAll(keyFactory.getDependencies());
        dependencies.addAll(valueFactory.getDependencies());
        return dependencies;
    }
}
