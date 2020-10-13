package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.ComparableFactory;
import den.vor.easy.object.factory.GenerationContext;

public class IntFactory extends ComparableFactory<Integer> {

    public IntFactory() {
        setMin(-1_000_000);
        setMax(1_000_000);
    }

    @Override
    public Integer getNext(Integer value) {
        return value + 1;
    }

    @Override
    public Integer getPrev(Integer value) {
        return value - 1;
    }

    @Override
    public Integer generate(GenerationContext context, Integer startInclusive, Integer endInclusive) {
        return context.getRandom().nextIntInclusive(startInclusive, endInclusive);
    }
}
