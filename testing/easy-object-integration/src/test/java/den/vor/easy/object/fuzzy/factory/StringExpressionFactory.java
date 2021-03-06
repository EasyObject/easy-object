package den.vor.easy.object.fuzzy.factory;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.impl.*;
import den.vor.easy.object.fuzzy.util.MapWeightRandom;
import den.vor.easy.object.random.CustomRandom;

import java.util.Map;
import java.util.function.BiFunction;

public class StringExpressionFactory {

    private final static MapWeightRandom<BiFunction<GenerationContext, CustomRandom, StringExpression>> EXPRESSIONS =
            new MapWeightRandom<>(
                    Map.of(
                            1., StringValueExpression::new
                    ));

    public static StringExpression getExpression(GenerationContext context, CustomRandom random) {
        return EXPRESSIONS.getNext().apply(context, random);
    }
}
