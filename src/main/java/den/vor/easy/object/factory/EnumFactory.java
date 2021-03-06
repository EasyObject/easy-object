package den.vor.easy.object.factory;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.value.Value;

/**
 * Factory that returns an object randomly chosen from some relatively small set.
 * @param <T> type of underlying value
 * @param <R> generated type, must extend {@link Value<T>}
 */
public class EnumFactory<T, R extends Value<T>> extends Factory<T, R> {

    private final EnumValuesProvider<R> enumValuesProvider;

    public EnumFactory(EnumValuesProvider<R> enumValuesProvider) {
        this.enumValuesProvider = enumValuesProvider;
    }

    @Override
    public Generator<R> getGenerator() {
        return enumValuesProvider::getNext;
    }
}
