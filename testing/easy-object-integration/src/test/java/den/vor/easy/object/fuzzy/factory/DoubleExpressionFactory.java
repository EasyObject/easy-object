package den.vor.easy.object.fuzzy.factory;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.impl.DoubleExpression;
import den.vor.easy.object.fuzzy.impl.DoubleValueExpression;
import den.vor.easy.object.fuzzy.impl.StringExpression;
import den.vor.easy.object.fuzzy.util.MapWeightRandom;
import den.vor.easy.object.random.CustomRandom;

import java.util.Map;
import java.util.function.BiFunction;

public class DoubleExpressionFactory {

    private final static MapWeightRandom<BiFunction<GenerationContext, CustomRandom, DoubleExpression>> EXPRESSIONS =
            new MapWeightRandom<>(
                    Map.of(
                            1., DoubleValueExpression::new
                    ));

    public static DoubleExpression getExpression(GenerationContext context, CustomRandom random) {
        return EXPRESSIONS.getNext().apply(context, random);
    }
}
