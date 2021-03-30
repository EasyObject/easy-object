/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

/**
 * Class that represents an empty value, analogue to {@code null}.
 * Should be used throw static final value - {@link NullValue#NULL}.
 */
public class NullValue extends Value<Object> {

    public static final NullValue NULL = new NullValue();

    private NullValue() {
        // Constructor is private to prevent you from creating additional NullValue instances. Use static instance.
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "NullValue{}";
    }
}
