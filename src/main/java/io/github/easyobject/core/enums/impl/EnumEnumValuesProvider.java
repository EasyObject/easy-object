/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.enums.impl;

import io.github.easyobject.core.enums.EnumValuesProvider;
import io.github.easyobject.core.value.ConstValue;
import io.github.easyobject.core.factory.GenerationContext;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Provider that returns all enum values with equal probability.
 *
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
