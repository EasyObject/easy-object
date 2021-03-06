package den.vor.easy.object.fuzzy.impl;

import den.vor.easy.object.fuzzy.FuzzyTestContext;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class StringValueExpression extends StringExpression {

    private String value;

    public StringValueExpression(GenerationContext context, CustomRandom random) {
        int length = random.nextInt(1, 10);
        value = Stream.generate(() -> random.nextInt(26))
                .limit(length)
                .mapToInt(i -> i + 'a')
                .mapToObj(i -> (char) i)
                .map(i -> i + "")
                .collect(joining());
    }

    @Override
    public String build() {
        return value + "";
    }

    @Override
    public String getExpected(FuzzyTestContext context) {
        return value;
    }

    @Override
    public String toString() {
        return "String{" + value + '}';
    }
}
