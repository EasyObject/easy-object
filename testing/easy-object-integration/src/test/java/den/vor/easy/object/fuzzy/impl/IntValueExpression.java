/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.impl;

import den.vor.easy.object.fuzzy.FuzzyTestContext;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;

public class IntValueExpression extends IntExpression {

    private int value;

    public IntValueExpression(GenerationContext context, CustomRandom random) {
        value = random.nextInt();
    }

    @Override
    public String build() {
        return value + "";
    }

    @Override
    public Integer getExpected(FuzzyTestContext context) {
        return value;
    }

    @Override
    public String toString() {
        return "Int{" + value + '}';
    }
}
