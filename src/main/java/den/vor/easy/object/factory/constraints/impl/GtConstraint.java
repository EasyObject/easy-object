package den.vor.easy.object.factory.constraints.impl;

import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.factory.constraints.SequenceConstraint;
import den.vor.easy.object.factory.constraints.SequenceConstraintsValues;

public class GtConstraint<T extends Comparable<? super T>> extends SequenceConstraint<T> {

    public GtConstraint(String constraint) {
        super(constraint);
    }

    @Override
    public SequenceConstraintsValues<T> apply(SequenceConstraintsValues<T> tsc, T value) {
        if (value.compareTo(tsc.getMin().getValue()) >= 0) {
            tsc.setMin(new Bound<>(value, false));
        }
        return tsc;
    }
}