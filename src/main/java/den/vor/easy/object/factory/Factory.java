/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static den.vor.easy.object.factory.FactoryConstants.COUNT_STRING_VALUE;

/**
 * Base class for all factories.
 * Represents an object that prepares a {@link Generator} to generate a sequence of values.
 * @param <T> type of generated values
 * @param <R> corresponding wrapper type that extends {@link Value<T>}
 */
public abstract class Factory<T, R extends Value<T>> {

    /**
     * Get a {@link Generator} that is able to produce values.
     * @return generator instance
     */
    public abstract Generator<R> getGenerator();

    /**
     * Get a list of references to the fields, that current factory depends on.
     * Is required to calculate the generation order for the fields of an object.
     * @return list of references.
     */
    public List<FieldRef> getDependencies() {
        return Collections.emptyList();
    }

    /**
     * Finishes construction of a factory and prepares it to generate the specified number of objects.
     * @param count number of objects to generate
     * @return {@link RootFactory} instance
     */
    public RootFactory<R> prepare(int count) {
        MapValue globalParams = new MapValue(Map.of(COUNT_STRING_VALUE, IntValue.of(count)));
        PreparationContext context = new PreparationContext().setGlobalParams(globalParams);
        prepareInternal(context);
        return new RootFactory<>(this, count);
    }

    /**
     * Finishes construction of a factory and prepares it to generate the {@link Integer#MAX_VALUE} objects.
     * @return {@link RootFactory} instance
     */
    public RootFactory<R> prepare() {
        PreparationContext context = new PreparationContext();
        prepareInternal(context);
        return new RootFactory<>(this, Integer.MAX_VALUE);
    }

    protected void prepareInternal(PreparationContext preparationContext) {
        // this is a stub implementation that does nothing. May be overridden in child classes.
    }
}
