/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy;

import den.vor.easy.object.random.DoubleWeightRandom;

import java.util.List;

public abstract class ElExpression<T> {

    public abstract String build();

    public abstract T getExpected(FuzzyTestContext context);

    protected int getNextExpression(List<Double> weights) {
        DoubleWeightRandom weightRandom = new DoubleWeightRandom(weights);
        return weightRandom.getNext();
    }
}
