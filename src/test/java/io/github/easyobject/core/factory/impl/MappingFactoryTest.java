/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;

import io.github.easyobject.core.factory.Factory;
import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.value.impl.IntValue;
import io.github.easyobject.core.factory.GenerationContext;
import io.github.easyobject.core.ref.FieldRef;
import io.github.easyobject.core.value.impl.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MappingFactoryTest {

    @Mock
    private Factory<String, StringValue> factory;
    @Mock
    private Generator<StringValue> generator;
    @Mock
    private Function<StringValue, IntValue> function;

    @Test
    void shouldReturnFactoryDependencies() {
        FieldRef dependency = new FieldRef(List.of(StringValue.of("key")), 0);
        List<FieldRef> dependencies = List.of(dependency);
        when(factory.getDependencies()).thenReturn(dependencies);
        MappingFactory<Integer, StringValue> mappingFactory = new MappingFactory<>(factory, function);

        List<FieldRef> result = mappingFactory.getDependencies();

        assertSame(dependencies, result, "Expected mappingFactory to return dependencies from nested factory");
    }

    @Test
    void shouldCallGeneratorAndApplyFunctionOnResult() {
        StringValue value = StringValue.of("a");
        IntValue intValue = IntValue.of(1);
        when(factory.getGenerator()).thenReturn(generator);
        when(generator.getNext(any(GenerationContext.class))).thenReturn(value);
        when(function.apply(value)).thenReturn(intValue);

        Integer result = new MappingFactory<>(factory, function)
                .getGenerator()
                .getNext(new GenerationContext())
                .getValue();

        assertEquals(1, result, "Expected result to be equal result of applying function to the " +
                "generated value");
        verify(factory, only()).getGenerator();
        verify(generator, only()).getNext(any(GenerationContext.class));
    }
}
