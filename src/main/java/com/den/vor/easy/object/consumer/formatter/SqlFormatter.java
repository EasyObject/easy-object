/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.consumer.formatter;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.MapValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.den.vor.easy.object.value.ScalarValue;
import com.den.vor.easy.object.value.impl.StringValue;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Formatter that transforms values into string in SQL DML format.
 */
public class SqlFormatter implements Formatter<String> {

    public static final String DEFAULT_DELIMITER = ",";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String VALUES = ")\nVALUES\n";

    private String tableName;
    private NestedFormat nestedFormat;

    public SqlFormatter(String tableName, NestedFormat nestedFormat) {
        this.tableName = tableName;
        this.nestedFormat = nestedFormat;
    }

    public SqlFormatter(String tableName) {
        this(tableName, new JsonNestedFormat());
    }

    private static String formatField(Value<?> value) {
        if (value instanceof StringValue) {
            return formatString(((StringValue) value).getValue());
        }
        return value.getValue().toString();
    }

    private static String formatString(String str) {
        return '"' + str.replace("\"", "\\\\\"") + '"';
    }

    @Override
    public String format(List<Value<?>> values) {
        if (values.isEmpty()) {
            return "";
        }
        Value<?> first = values.get(0);
        if (first instanceof MapValue) {
            return nestedFormat.format(tableName, values);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String format(Value<?> value) {
        return format(List.of(value));
    }

    /**
     * Determines how to handle nested objects.
     */
    public interface NestedFormat {

        /**
         * Format a list of values with a specified table name.
         * @return formatted string
         */
        String format(String tableName, List<Value<?>> values);
    }

    /**
     * Nested format that transforms nested objects into a separate table that can be joined with an original one.
     * Currently supports joining on "id" field only.
     */
    public static class JoinTableNestedFormat implements NestedFormat {

        public String format(String tableName, List<Value<?>> values) {

            List<String> result = new ArrayList<>(values.size());
            MapValue mapValue = (MapValue) values.get(0);
            List<ScalarValue<?>> scalarKeys = mapValue.getValue().entrySet().stream()
                    .filter(e -> !(e.getValue() instanceof MapValue))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            List<ScalarValue<?>> compoundKeys = mapValue.getValue().entrySet().stream()
                    .filter(e -> e.getValue() instanceof MapValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // get matrix of scalar values

            Map<String, List<JoinFormatCompoundValueHolder>> compoundValues = new HashMap<>();

            for (Value<?> value : values) {
                String row = scalarKeys.stream()
                        .map(key -> formatField(value.get(key)))
                        .collect(Collectors.joining(DEFAULT_DELIMITER));
                result.add("(" + row + ")");

                if (!compoundKeys.isEmpty()) {
                    Value<?> id = value.get(StringValue.of("id"));
                    if (id == null) {
                        throw new UnsupportedOperationException("Temporary can join on id field only");
                    }
                    compoundKeys.forEach(k -> {
                        JoinFormatCompoundValueHolder holder = new JoinFormatCompoundValueHolder()
                                .setValue(value.get(k))
                                .setParentId(id);
                        compoundValues.computeIfAbsent(k.getValue().toString(),
                                ignored -> new ArrayList<>()).add(holder);
                    });
                }
            }
            String valuesSql = String.join(",\n", result);
            String columns = scalarKeys.stream()
                    .map(Value::getValue)
                    .map(Object::toString)
                    .collect(Collectors.joining(DEFAULT_DELIMITER));
            String headerSql = INSERT_INTO + tableName + " (" + columns + VALUES;
            String table = headerSql + valuesSql + ";";
            String childTables = compoundValues.entrySet().stream()
                    .map(e -> formatChildTable(e.getKey(), e.getValue()))
                    .collect(Collectors.joining("\n\n"));
            return table + "\n\n" + childTables;
        }

        public String formatChildTable(String tableName, List<JoinFormatCompoundValueHolder> values) {

            List<String> result = new ArrayList<>(values.size());
            MapValue mapValue = (MapValue) values.get(0).getValue();
            List<ScalarValue<?>> scalarKeys = mapValue.getValue().entrySet().stream()
                    .filter(e -> !(e.getValue() instanceof MapValue))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            List<ScalarValue<?>> compoundKeys = mapValue.getValue().entrySet().stream()
                    .filter(e -> e.getValue() instanceof MapValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // get matrix of scalar values

            Map<String, List<JoinFormatCompoundValueHolder>> compoundValues = new HashMap<>();

            for (JoinFormatCompoundValueHolder holder : values) {
                Value<?> value = holder.getValue();
                String row = scalarKeys.stream()
                        .map(key -> formatField(value.get(key)))
                        .collect(Collectors.joining(DEFAULT_DELIMITER));
                result.add("(" + formatField(holder.parentId) + DEFAULT_DELIMITER + row + ")");
                if (!compoundKeys.isEmpty()) {
                    Value<?> id = value.get(StringValue.of("id"));
                    if (id == null) {
                        throw new UnsupportedOperationException("Temporary can join on id field only");
                    }
                    compoundKeys.forEach(k -> {
                        JoinFormatCompoundValueHolder h = new JoinFormatCompoundValueHolder()
                                .setValue(value.get(k))
                                .setParentId(id);
                        compoundValues.computeIfAbsent(k.getValue().toString(), ignored -> new ArrayList<>()).add(h);
                    });
                }
            }
            String valuesSql = String.join(",\n", result);
            String columns = scalarKeys.stream()
                    .map(Value::getValue)
                    .map(Object::toString)
                    .collect(Collectors.joining(DEFAULT_DELIMITER));
            String headerSql = INSERT_INTO + tableName + " (parentId, " + columns + VALUES;
            String table = headerSql + valuesSql + ";";
            String childTables = compoundValues.entrySet().stream()
                    .map(e -> formatChildTable(e.getKey(), e.getValue()))
                    .collect(Collectors.joining("\n\n"));
            return table + "\n\n" + childTables;
        }
    }

    protected static class JoinFormatCompoundValueHolder {
        private Value<?> value;
        private Value<?> parentId;

        public Value<?> getValue() {
            return value;
        }

        public JoinFormatCompoundValueHolder setValue(Value<?> value) {
            this.value = value;
            return this;
        }

        public Value<?> getParentId() {
            return parentId;
        }

        public JoinFormatCompoundValueHolder setParentId(Value<?> parentId) {
            this.parentId = parentId;
            return this;
        }
    }

    /**
     * Nested format that transforms nested values into json and writes them to the original table.
     */
    public static class JsonNestedFormat implements NestedFormat {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String format(String tableName, List<Value<?>> values) {

            List<String> result = new ArrayList<>(values.size());
            MapValue mapValue = (MapValue) values.get(0);
            List<ScalarValue<?>> keys = new ArrayList<>(mapValue.getValue().keySet());

            // get matrix of scalar values

            for (Value<?> value : values) {
                String row = keys.stream().map(key -> {
                    Value<?> field = value.get(key);
                    if (field instanceof ScalarValue) {
                        return formatField(field);
                    }
                    try {
                        return formatString(objectMapper.writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        throw new UncheckedIOException(e);
                    }
                }).collect(Collectors.joining(DEFAULT_DELIMITER));
                result.add("(" + row + ")");
            }
            String valuesSql = String.join(",\n", result);
            String columns = keys.stream()
                    .map(Value::getValue)
                    .map(Object::toString)
                    .collect(Collectors.joining(DEFAULT_DELIMITER));
            String headerSql = INSERT_INTO + tableName + " (" + columns + VALUES;
            return headerSql + valuesSql + ";";
        }
    }
}
