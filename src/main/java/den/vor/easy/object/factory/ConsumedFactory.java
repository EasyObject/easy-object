/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.consumer.Consumer;
import den.vor.easy.object.value.Value;

import java.util.List;

public class ConsumedFactory<R extends Value<?>> {

    private RootFactory<R> rootFactory;
    private List<Consumer<?>> consumers;

    public ConsumedFactory(RootFactory<R> rootFactory, List<Consumer<?>> consumers) {
        this.rootFactory = rootFactory;
        this.consumers = consumers;
    }

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
