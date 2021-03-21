/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.consumer;

import den.vor.easy.object.consumer.formatter.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Consumer that does nothing with values. Development purpose only.
 * @param <T> type of value returned by {@link Formatter}s
 */
public class NoOpConsumer<T> extends Consumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpConsumer.class);

    public NoOpConsumer(Formatter<T>... formatters) {
        super(formatters);
    }

    @Override
    protected void doConsume(T toConsume) {
        LOGGER.debug("Consuming a value");
    }
}
