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
