/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.consumer.formatter;

import com.den.vor.easy.object.parser.ExpressionEvaluator;
import com.den.vor.easy.object.value.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formatter that transforms values into string in json format.
 */
public class JsonFormatter implements Formatter<String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ExpressionEvaluator expressionEvaluator;

    /**
     * Creates a new formatter that transforms a result of expression evaluation on values.
     *
     * @param expression expression to evaluate on each object formatted.
     */
    public JsonFormatter(String expression) {
        this.expressionEvaluator = new ExpressionEvaluator(expression);
    }

    public JsonFormatter() {
    }

    @Override
    public String format(List<Value<?>> values) {
        try {
            if (expressionEvaluator == null) {
                return objectMapper.writeValueAsString(values);
            } else {
                List<Value<?>> mapped = values.stream().map(expressionEvaluator::evaluate).collect(Collectors.toList());
                return objectMapper.writeValueAsString(mapped);
            }
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String format(Value<?> value) {
        try {
            if (expressionEvaluator == null) {
                return objectMapper.writeValueAsString(value);
            } else {
                Value<?> mapped = expressionEvaluator.evaluate(value);
                return objectMapper.writeValueAsString(mapped);
            }
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
