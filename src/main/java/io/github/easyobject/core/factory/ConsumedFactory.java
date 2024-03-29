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
import io.github.easyobject.core.consumer.Consumer;

import java.util.List;

/**
 * Class that encapsulates a {@link RootFactory} with all {@link Consumer}s.
 * @param <R> type of value generated by {@link RootFactory}
 */
public class ConsumedFactory<R extends Value<?>> {

    private RootFactory<R> rootFactory;
    private List<Consumer<?>> consumers;

    public ConsumedFactory(RootFactory<R> rootFactory, List<Consumer<?>> consumers) {
        this.rootFactory = rootFactory;
        this.consumers = consumers;
    }

    /**
     * Starts the generation process.
     * Values, generated by {@linkplain ConsumedFactory#rootFactory} are consumed by all specified consumers.
     */
    public void generate() {
        rootFactory.stream().forEach(this::consumeValue);
        for (Consumer<?> consumer : consumers) {
            consumer.flush();
        }
    }

    private void consumeValue(R value) {
        for (Consumer<?> consumer : consumers) {
            consumer.consume(value);
        }
    }
}
