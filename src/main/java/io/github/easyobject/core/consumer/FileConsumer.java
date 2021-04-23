/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.consumer;

import io.github.easyobject.core.consumer.formatter.Formatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Consumer that writes formatted to string values to the file.
 * Creates output stream on each write operation. Should be used without batch with great caution
 */
public class FileConsumer extends Consumer<String> {

    private final String fileName;

    public FileConsumer(int batchSize, String fileName, Formatter<String>... formatters) {
        super(batchSize, formatters);
        this.fileName = fileName;
    }

    public FileConsumer(boolean useBatch, String fileName, Formatter<String>... formatters) {
        super(useBatch, formatters);
        this.fileName = fileName;
    }

    public FileConsumer(String fileName, Formatter<String>... formatters) {
        super(formatters);
        this.fileName = fileName;
    }

    @Override
    protected void doConsume(String toConsume) {
        Path path = Paths.get(fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, toConsume + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
