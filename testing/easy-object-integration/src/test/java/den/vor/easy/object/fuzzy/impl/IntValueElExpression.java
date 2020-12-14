package den.vor.easy.object.fuzzy.impl;

import den.vor.easy.object.fuzzy.FuzzyTestContext;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.ValueElExpression;
import den.vor.easy.object.random.CustomRandom;

public class IntValueElExpression extends IntExpression {

    private int value;

    public IntValueElExpression(GenerationContext context, CustomRandom random) {
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
