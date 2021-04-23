/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.facade;

import com.den.vor.easy.object.consumer.Consumer;
import com.den.vor.easy.object.consumer.FileConsumer;
import com.den.vor.easy.object.consumer.StdConsumer;
import com.den.vor.easy.object.consumer.formatter.Formatter;
import com.den.vor.easy.object.consumer.formatter.JsonFormatter;

/**
 * Utility class for objects consumption.
 */
public class ConsumerFacade {

    /**
     * Creates a consumer that formats objects by provided formatters and writes the result to the file.
     * Each object will be formatted by each formatter.
     * If the file does not exist, it will be created. All required parent directories will be created too.
     * @param path target file path
     * @param formatters list of formats
     * @return consumer instance
     */
    public static FileConsumer toFile(String path, Formatter<String>... formatters) {
        return new FileConsumer(path, formatters);
    }

    /**
     * Creates a consumer that formats objects by provided formatters and writes the result to the console.
     * Each object will be formatted by each formatter.
     * @param formatters list of formats
     * @return consumer instance
     */
    public static StdConsumer toStd(Formatter<String>... formatters) {
        return new StdConsumer(formatters);
    }

    /**
     * Creates a formatter that prints whole objects to JSON.
     * Can be later used by {@link Consumer}.
     * @return formatter instance
     */
    public static JsonFormatter toJson() {
        return new JsonFormatter();
    }

    /**
     * Creates a formatter that applies a given expression to the objects and writes the results to JSON.
     * Can be used to print only a part of the object (one field, for example).
     * @param expression expression to evaluate on each object
     * @return formatter instance
     */
    public static JsonFormatter toJson(String expression) {
        return new JsonFormatter(expression);
    }

    private ConsumerFacade() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
