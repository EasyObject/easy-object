/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.factory;

import den.vor.easy.object.fuzzy.ElExpression;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.impl.XorShift1024StarProvider;

public class ElExpressionFactory {

    public static ElExpression<?> generate() {
        CustomRandom customRandom = new XorShift1024StarProvider().getRandom();
        return generate(customRandom);
    }

    public static ElExpression<?> generate(CustomRandom customRandom) {
        GenerationContext context = new GenerationContext();
        return IntExpressionFactory.getExpression(context, customRandom);
    }
}
