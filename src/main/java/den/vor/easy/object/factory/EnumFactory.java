package den.vor.easy.object.factory;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.value.Value;

public class EnumFactory<T, R extends Value<T>> extends Factory<T, R> {

    private final EnumValuesProvider<R> enumValuesProvider;

    public EnumFactory(EnumValuesProvider<R> enumValuesProvider) {
        this.enumValuesProvider = enumValuesProvider;
    }

    @Override
    public Generator<R> getGenerator() {
        return context -> enumValuesProvider.getNext(context);
    }
}
