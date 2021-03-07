/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;


import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.value.ConstValue;
import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

public class ConstFactoryTest {

    @Test
    public void shouldReturnTheSameObject() {
        Object value = new Object();

        ConstFactory<Object> constFactory = new ConstFactory<>(new ConstValue<>(value));
        Generator<Value<Object>> generator = constFactory.getGenerator();

        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()).getValue(), sameInstance(value));
    }
}
