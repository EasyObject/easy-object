/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value;

import io.github.easyobject.core.value.impl.StringValue;

/**
 * Base class for values, that do not carry other values in any form (like collections).
 * @param <T> type of encapsulated object
 */
public abstract class ScalarValue<T> extends Value<T> {

    /**
     * Returns {@link StringValue} with value's representation.
     */
    public StringValue toStringValue() {
        throw new UnsupportedOperationException();
    }
}
