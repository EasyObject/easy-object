package den.vor.easy.object.enums.impl;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.factory.GenerationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provider that returns all values from provided collection with equal probability
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
