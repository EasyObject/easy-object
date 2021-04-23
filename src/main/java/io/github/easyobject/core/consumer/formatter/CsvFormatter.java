/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.consumer.formatter;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.MapValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.easyobject.core.value.ScalarValue;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formatter that returns values in a comma-separated format.
 */
public class CsvFormatter implements Formatter<String> {

    public static final String DEFAULT_HEADER_NAME = "value";
    public static final String DEFAULT_DELIMITER = ",";

    private boolean headers;
    private String delimiter;
    private NestedFormat nestedFormat;

    /**
     * Creates a new formatter instance.
     *
     * @param headers      flag that indicates whether to use headers row
     * @param delimiter    delimiter to use
     * @param nestedFormat formatter for nested objects, such as maps and lists
     */
    public CsvFormatter(boolean headers, String delimiter, NestedFormat nestedFormat) {
        this.headers = headers;
        this.delimiter = delimiter;
        this.nestedFormat = nestedFormat;
    }

    public CsvFormatter(boolean headers, String delimiter) {
        this(headers, delimiter, new JsonNestedFormat());
    }

    public CsvFormatter() {
        this(true, DEFAULT_DELIMITER);
    }

    @Override
    public String format(List<Value<?>> values) {
        if (values.isEmpty()) {
            return "";
        }
        Value<?> first = values.get(0);
        String header = "";
        if (first instanceof ScalarValue) {
            if (headers) {
                header = DEFAULT_HEADER_NAME + '\n';
            }
            return header + values.stream()
                    .map(Value::getValue)
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
        } else if (first instanceof MapValue) {
            List<String> result = new ArrayList<>(values.size());
            MapValue mapValue = (MapValue) first;
            List<ScalarValue<?>> keys = new ArrayList<>(mapValue.getValue().keySet());

            if (headers) {
                header = keys.stream().map(Value::getValue)
                        .map(Object::toString)
                        .collect(Collectors.joining(delimiter)) + '\n';
            }

            for (Value<?> value : values) {
                String row = keys.stream().map(key -> {
                    Value<?> field = value.get(key);
                    if (field instanceof ScalarValue) {
                        return field.getValue().toString();
                    }
                    return nestedFormat.format(field);
                }).collect(Collectors.joining(delimiter));
                result.add(row);
            }

            return header + String.join("\n", result);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String format(Value<?> value) {
        return format(List.of(value));
    }

    public interface NestedFormat {
        String format(Value<?> value);
    }

    public static class JsonNestedFormat implements NestedFormat {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String format(Value<?> value) {
            try {
                return objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

}
