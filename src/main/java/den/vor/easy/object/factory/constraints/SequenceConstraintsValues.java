/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.constraints;

/**
 * Specifies both bounds (upper and lower) for {@link den.vor.easy.object.factory.ComparableFactory}.
 *
 * @param <T> type of bound value
 */
public class SequenceConstraintsValues<T> {

    private Bound<T> min;
    private Bound<T> max;

    public SequenceConstraintsValues(Bound<T> min, Bound<T> max) {
        this.min = min;
        this.max = max;
    }

    public Bound<T> getMin() {
        return min;
    }

    public void setMin(Bound<T> min) {
        this.min = min;
    }

    public Bound<T> getMax() {
        return max;
    }

    public void setMax(Bound<T> max) {
        this.max = max;
    }
}
