/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.factory;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.impl.IntExpression;
import den.vor.easy.object.fuzzy.impl.IntValueExpression;
import den.vor.easy.object.fuzzy.impl.IntegerBinaryArithmeticExpression;
import den.vor.easy.object.fuzzy.util.MapWeightRandom;
import den.vor.easy.object.random.CustomRandom;

import java.util.Map;
import java.util.function.BiFunction;

public class IntExpressionFactory {

    private static final int MAX_DEPTH = 10;

    private static final MapWeightRandom<BiFunction<GenerationContext, CustomRandom, IntExpression>> EXPRESSIONS =
            new MapWeightRandom<>(
                    Map.of(
                            0.51, IntValueExpression::new,
                            0.49, IntegerBinaryArithmeticExpression::new
                    ));

    private static final BiFunction<GenerationContext, CustomRandom, IntExpression> LEAF_GENERATOR =
            IntValueExpression::new;

    public static IntExpression getExpression(GenerationContext context, CustomRandom random) {
        if (context.getDepth() == MAX_DEPTH) {
            return LEAF_GENERATOR.apply(context, random);
        }
        return EXPRESSIONS.getNext().apply(context, random);
    }
}
