/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.constraints;

import io.github.easyobject.core.factory.ComparableFactory;

/**
 * Contains info about bound for {@link ComparableFactory} values.
 *
 * @param <T> type of bound value
 */
public class Bound<T> {

    private final T value;
    private final boolean inclusive;

    /**
     * Creates a new bound.
     *
     * @param value     bound value
     * @param inclusive flag that specifies whether this bound is inclusive or not
     */
    public Bound(T value, boolean inclusive) {
        this.value = value;
        this.inclusive = inclusive;
    }

    public T getValue() {
        return value;
    }

    public boolean isInclusive() {
        return inclusive;
    }
}
