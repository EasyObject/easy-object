/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;

import io.github.easyobject.core.factory.*;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.factory.Factory;
import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.factory.ValueSource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Pools the values returns by {@link PoolFactory#tFactory}.
 * Factory returns a random value from the pool with equal probability. Each pool entry has an associated
 * time to live - number of times it will be returned by the factory before been replaced.
 * Time to live is specified by {@link PoolFactory#ttlFactory}.
 * If you need an infinite TTL use {@link EnumFactory} for better performance.
 *
 * PoolFactory is lock-free
 * @param <T> type of underlying value
 */
public class PoolFactory<T> extends Factory<T, Value<T>> {

    private final ValueSource<T> tFactory;
    private final int size;
    private final ValueSource<Integer> ttlFactory;

    public PoolFactory(ValueSource<T> tFactory, int size, ValueSource<Integer> ttlFactory) {
        this.tFactory = tFactory;
        this.size = size;
        this.ttlFactory = ttlFactory;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        Generator<? extends Value<T>> tGenerator = tFactory.getGenerator();
        Generator<? extends Value<Integer>> timeToLiveGenerator = ttlFactory.getGenerator();

        List<PoolEntry<Value<T>>> elements = Stream.generate(() -> new PoolEntry<Value<T>>(null, -1))
                .limit(size)
                .collect(Collectors.toList());

        return context -> {
            while (true) {
                int index = context.getRandom().nextInt(size);
                PoolEntry<Value<T>> chosen = elements.get(index);
                int timeToLive = chosen.timeToLive.decrementAndGet();
                if (timeToLive == 0) {
                    PoolEntry<Value<T>> element = new PoolEntry<>(tGenerator.getNext(context),
                            timeToLiveGenerator.getNext(context).getValue());
                    elements.set(index, element);
                    return chosen.element;
                } else if (timeToLive > 0) {
                    return chosen.element;
                }
            }
        };
    }

    protected static class PoolEntry<T> {

        private final T element;
        private final AtomicInteger timeToLive;

        private PoolEntry(T element, int timeToLive) {
            this.element = element;
            this.timeToLive = new AtomicInteger(timeToLive);
        }

        @Override
        public String toString() {
            return "PoolEntry{" +
                    "element=" + element +
                    ", timeToLive=" + timeToLive +
                    '}';
        }
    }
}
