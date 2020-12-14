package den.vor.easy.object.factory.constraints.impl;

import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.factory.constraints.SequenceConstraint;
import den.vor.easy.object.factory.constraints.SequenceConstraintsValues;

public class LeConstraint<T extends Comparable<? super T>> extends SequenceConstraint<T> {

    public LeConstraint(String constraint) {
        super(constraint);
    }

    @Override
    public SequenceConstraintsValues<T> apply(SequenceConstraintsValues<T> tsc, T value) {
        if (value.compareTo(tsc.getMax().getValue()) < 0) {
            tsc.setMax(new Bound<>(value, true));
        }
        return tsc;
    }
}