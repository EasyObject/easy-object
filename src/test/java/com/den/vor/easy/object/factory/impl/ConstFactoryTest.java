/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory.impl;


import com.den.vor.easy.object.factory.Generator;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.factory.GenerationContext;
import com.den.vor.easy.object.value.ConstValue;
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
