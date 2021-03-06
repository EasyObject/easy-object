package den.vor.easy.object.fuzzy.impl;

import den.vor.easy.object.fuzzy.FuzzyTestContext;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;

public class DoubleValueExpression extends DoubleExpression {

    private double value;

    public DoubleValueExpression(GenerationContext context, CustomRandom random) {
        value = random.nextDouble();
    }

    @Override
    public String build() {
        return value + "";
    }

    @Override
    public Double getExpected(FuzzyTestContext context) {
        return value;
    }

    @Override
    public String toString() {
        return "Double{" + value + '}';
    }
}
