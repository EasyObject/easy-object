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
