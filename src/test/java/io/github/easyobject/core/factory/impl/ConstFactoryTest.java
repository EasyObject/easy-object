/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;


import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.factory.GenerationContext;
import io.github.easyobject.core.value.ConstValue;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

class ConstFactoryTest {

    @Test
    void shouldReturnTheSameObject() {
        Object value = new Object();

        ConstFactory<Object> constFactory = new ConstFactory<>(new ConstValue<>(value));
        Generator<Value<Object>> generator = constFactory.getGenerator();

        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
    }
}
