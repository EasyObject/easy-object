package den.vor.easy.object.enums.impl;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.value.ConstValue;
import den.vor.easy.object.value.Value;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Provider that returns all enum values with equal probability
 * @param <T> enum class to get values from
 */
public class EnumEnumValuesProvider<T extends Enum<T>> implements EnumValuesProvider<ConstValue<T>> {

    private final List<ConstValue<T>> enumConstants;

    public EnumEnumValuesProvider(Class<T> tClass) {
        this.enumConstants = Arrays.stream(tClass.getEnumConstants()).map(ConstValue::new).collect(toList());
    }

    @Override
    public ConstValue<T> getNext(GenerationContext context) {
        return context.getRandom().next(enumConstants);
    }
}
