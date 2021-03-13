/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.v2;

import java.beans.Expression;

public class WeightedExpression {
    
    private final Expression expression;
    private final double weight;

    public WeightedExpression(Expression expression, double weight) {
        this.expression = expression;
        this.weight = weight;
    }

    public Expression getExpression() {
        return expression;
    }

    public double getWeight() {
        return weight;
    }
}
