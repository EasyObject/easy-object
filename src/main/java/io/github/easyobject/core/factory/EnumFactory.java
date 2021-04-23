/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.enums.EnumValuesProvider;

/**
 * Factory that returns an object randomly chosen from some relatively small set.
 *
 * @param <T> type of underlying value
 * @param <R> generated type, must extend {@link Value}
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
