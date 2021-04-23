/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;

import io.github.easyobject.core.factory.Factory;
import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.value.Value;

/**
 * Factory, which {@link Generator} always returns the same value.
 *
 * @param <T> type of value
 */
public class ConstFactory<T> extends Factory<T, Value<T>> {

    private final Value<T> value;

    /**
     * Creates new factory instance.
     *
     * @param value value to return
     */
    public ConstFactory(Value<T> value) {
        this.value = value;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        return context -> value;
    }
}
