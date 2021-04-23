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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory that returns elements specified by {@link RoundRobinFactory#values} field one by one.
 *
 * @param <T> type of value returned
 */
public class RoundRobinFactory<T> extends Factory<T, Value<T>> {

    private final List<? extends Value<T>> values;

    public RoundRobinFactory(List<? extends Value<T>> values) {
        this.values = values;
    }

    public RoundRobinFactory(Value<T>... values) {
        this.values = Arrays.asList(values);
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        AtomicInteger index = new AtomicInteger();
        return context -> {
            int nextIndex = index.getAndUpdate(i -> (i + 1) % values.size());
            return values.get(nextIndex);
        };
    }
}
