/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.ref.FieldRef;

import java.util.Collections;
import java.util.List;

public abstract class ValueSource<T> {

    /**
     * Get a {@link Generator} that is able to produce values.
     *
     * @return generator instance
     */
    public abstract Generator<? extends Value<T>> getGenerator();

    /**
     * Get a list of references to the fields, that current factory depends on.
     * Is required to calculate the generation order for the fields of an object.
     *
     * @return list of references.
     */
    public List<FieldRef> getDependencies() {
        return Collections.emptyList();
    }
}
