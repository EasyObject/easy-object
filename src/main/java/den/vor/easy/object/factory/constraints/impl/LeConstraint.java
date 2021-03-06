/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.constraints.impl;

import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.factory.constraints.SequenceConstraint;
import den.vor.easy.object.factory.constraints.SequenceConstraintsValues;

/**
 * Less-than-or-equals constraint (<=).
 * Is applied to the upper bound. Newly set bound is <b>inclusive</b>.
 *
 * @param <T> type of object, generated by the factory
 */
public class LeConstraint<T extends Comparable<? super T>> extends SequenceConstraint<T> {

    public LeConstraint(String constraint) {
        super(constraint);
    }

    @Override
    public SequenceConstraintsValues<T> apply(SequenceConstraintsValues<T> constraintsValues, T value) {
        if (value.compareTo(constraintsValues.getMax().getValue()) < 0) {
            constraintsValues.setMax(new Bound<>(value, true));
        }
        return constraintsValues;
    }
}
