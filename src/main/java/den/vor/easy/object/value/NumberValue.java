/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

/**
 * Base class for values that encapsulate {@link Number} value.
 * @param <T> type of encapsulated object
 */
public abstract class NumberValue<T extends Number & Comparable<T>> extends ComparableValue<T> {

    /**
     * Default comparison method implementation.
     * If both numbers are instances of one class, delegates comparison to them. If not - comparison is made by the
     * double values (see {@linkplain Number#doubleValue()}).
     * @param value value to compare with.
     * @return comparison result.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected int compareTo(Value<?> value) {
        Object second = value.getValue();
        if (!(second instanceof Number)) {
            throw new UnsupportedOperationException();
        }
        T thisValue = getValue();
        if (thisValue.getClass().isInstance(second)) {
            return thisValue.compareTo((T) second);
        }
        Double secondDouble = ((Number) second).doubleValue();
        Double thisDouble = thisValue.doubleValue();
        return thisDouble.compareTo(secondDouble);
    }
}
