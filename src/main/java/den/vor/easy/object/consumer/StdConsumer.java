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

/**
 * Consumer that formats all values to string and prints them to console.
 * Mainly for development purposes.
 */
public class StdConsumer extends Consumer<String> {

    public StdConsumer(int batchSize, Formatter<String>... formatters) {
        super(batchSize, formatters);
    }

    public StdConsumer(boolean useBatch, Formatter<String>... formatters) {
        super(useBatch, formatters);
    }

    public StdConsumer(Formatter<String>... formatters) {
        super(formatters);
    }

    @Override
    protected void doConsume(String toConsume) {
        System.out.println(toConsume);
    }
}
