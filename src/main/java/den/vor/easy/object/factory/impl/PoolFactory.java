/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PoolFactory<T> extends Factory<T, Value<T>> {

    private final Factory<T, Value<T>> tFactory;
    private final int size;
    private final Factory<Integer, ? extends Value<Integer>> timesToLive;

    public PoolFactory(Factory<T, Value<T>> tFactory,
                       int size,
                       Factory<Integer, ? extends Value<Integer>> ttlFactory) {
        this.tFactory = tFactory;
        this.size = size;
        this.timesToLive = ttlFactory;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        Generator<Value<T>> tGenerator = tFactory.getGenerator();
        Generator<? extends Value<Integer>> timeToLiveGenerator = timesToLive.getGenerator();

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
