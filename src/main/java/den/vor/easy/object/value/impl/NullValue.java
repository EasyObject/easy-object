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

public class NullValue extends Value<Object> {

    public static final NullValue NULL = new NullValue();

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "NullValue{}";
    }
}
