package den.vor.easy.object.consumer.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFormatter implements Formatter<String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ExpressionEvaluator expressionEvaluator;

    public JsonFormatter(String path) {
        this.expressionEvaluator = new ExpressionEvaluator(path, MapValue.emptyMap());
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
