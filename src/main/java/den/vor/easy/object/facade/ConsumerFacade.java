/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.facade;

import den.vor.easy.object.consumer.FileConsumer;
import den.vor.easy.object.consumer.StdConsumer;
import den.vor.easy.object.consumer.formatter.Formatter;
import den.vor.easy.object.consumer.formatter.JsonFormatter;

public class ConsumerFacade {

    public static FileConsumer toFile(String path, Formatter<String>... formatters) {
        return new FileConsumer(path, formatters);
    }

    public static StdConsumer toStd(Formatter<String>... formatters) {
        return new StdConsumer(formatters);
    }

    public static StdConsumer toStd(boolean useBatch, Formatter<String>... formatters) {
        return new StdConsumer(useBatch, formatters);
    }

    public static JsonFormatter toJson() {
        return new JsonFormatter();
    }

    public static JsonFormatter toJson(String path) {
        return new JsonFormatter(path);
    }

    private ConsumerFacade() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
