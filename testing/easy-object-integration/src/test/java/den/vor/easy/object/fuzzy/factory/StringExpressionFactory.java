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
import den.vor.easy.object.fuzzy.impl.*;
import den.vor.easy.object.fuzzy.util.MapWeightRandom;
import den.vor.easy.object.random.CustomRandom;

import java.util.Map;
import java.util.function.BiFunction;

public class StringExpressionFactory {

    private static final MapWeightRandom<BiFunction<GenerationContext, CustomRandom, StringExpression>> EXPRESSIONS =
            new MapWeightRandom<>(
                    Map.of(
                            1., StringValueExpression::new
                    ));

    public static StringExpression getExpression(GenerationContext context, CustomRandom random) {
        return EXPRESSIONS.getNext().apply(context, random);
    }
}
