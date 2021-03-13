/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.v2.util;

import den.vor.easy.object.fuzzy.v2.WeightedExpression;
import den.vor.easy.object.random.DoubleWeightRandom;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MapWeightRandom {

    private List<Expression> values;
    private DoubleWeightRandom doubleWeightRandom;

    public MapWeightRandom(Collection<WeightedExpression> expressions) {
        List<Double> weights = new ArrayList<>();
        this.values = new ArrayList<>();

        for (WeightedExpression expression : expressions) {
            weights.add(expression.getWeight());
            values.add(expression.getExpression());
        }

        doubleWeightRandom = new DoubleWeightRandom(weights);
    }

    public Expression getNext() {
        return values.get(doubleWeightRandom.getNext());
    }
}
