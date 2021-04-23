/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value;

import io.github.easyobject.core.value.impl.BooleanValue;

/**
 * Base class for values that encapsulate {@link Comparable} objects.
 * Provides basic implementation of comparison operators. See {@link ComparisonAware} for details.
 * @param <T> type of underlying value
 */
public abstract class ComparableValue<T extends Comparable<? super T>> extends ScalarValue<T> {

    @Override
    public BooleanValue gt(Value<?> value) {
        return BooleanValue.of(compareTo(value) > 0);
    }

    @Override
    public BooleanValue gte(Value<?> value) {
        return BooleanValue.of(compareTo(value) >= 0);
    }

    @Override
    public BooleanValue lte(Value<?> value) {
        return BooleanValue.of(compareTo(value) <= 0);
    }

    @Override
    public BooleanValue lt(Value<?> value) {
        return BooleanValue.of(compareTo(value) < 0);
    }

    @Override
    public BooleanValue equalTo(Value<?> value) {
        return BooleanValue.of(compareTo(value) == 0);
    }

    @Override
    public BooleanValue notEqualTo(Value<?> value) {
        return BooleanValue.of(compareTo(value) != 0);
    }

    @SuppressWarnings("unchecked")
    protected int compareTo(Value<?> value) {
        Object second = value.getValue();
        if (!getValue().getClass().isInstance(second)) {
            throw new UnsupportedOperationException();
        }
        return getValue().compareTo((T) second);
    }
}
