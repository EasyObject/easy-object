/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.enums.impl;

import den.vor.easy.object.enums.EnumValuesProvider;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.value.impl.StringValue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provider that reads values from file and returns them with equal probability.
 *
 * By default, each line is considered to be a separate value.
 * If you specify a delimiter, file lines will be joined and then split by the provided delimiter.
 */
public class FileEnumValuesProvider implements EnumValuesProvider<StringValue> {

    public static final String DEFAULT_DELIMITER = "\n";
    private List<StringValue> values;

    public FileEnumValuesProvider(Path path, String delimiter) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            if (!"\n".equals(delimiter)) {
                String fileContent = String.join("", lines);
                this.values = wrap(Arrays.asList(fileContent.split(delimiter)));
            } else {
                this.values = wrap(lines);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<StringValue> wrap(List<String> strings) {
        return strings.stream().map(StringValue::of).collect(Collectors.toList());
    }

    public FileEnumValuesProvider(Path path) {
        this(path, DEFAULT_DELIMITER);
    }

    @Override
    public StringValue getNext(GenerationContext context) {
        return context.getRandom().next(values);
    }
}
