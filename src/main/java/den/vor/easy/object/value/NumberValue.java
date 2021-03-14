/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

public abstract class NumberValue<T extends Number & Comparable<T>> extends ComparableValue<T> {

    @Override
    @SuppressWarnings("unchecked")
    protected int compareTo(Value<?> value) {
        Object second = value.getValue();
        if (!(second instanceof Number)) {
            throw new UnsupportedOperationException();
        }
        if (getValue().getClass().isInstance(second)) {
            return getValue().compareTo((T) second);
        }
        Double secondDouble = ((Number) second).doubleValue();
        Double thisDouble = getValue().doubleValue();
        return thisDouble.compareTo(secondDouble);
    }
}
