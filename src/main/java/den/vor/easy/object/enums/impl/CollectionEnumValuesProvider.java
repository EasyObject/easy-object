/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.enums.impl;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.factory.GenerationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provider that returns all values from provided collection with equal probability.
 *
 * @param <T> type of values inside the collection
 */
public class CollectionEnumValuesProvider<T> implements EnumValuesProvider<T> {

    private final List<T> values;

    public CollectionEnumValuesProvider(Collection<T> values) {
        this.values = new ArrayList<>(values);
    }

    @Override
    public T getNext(GenerationContext context) {
        return context.getRandom().next(values);
    }
}
