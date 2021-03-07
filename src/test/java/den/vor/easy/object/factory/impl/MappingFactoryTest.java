/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;
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
public class MappingFactoryTest {

    @Mock
    private Factory<String, StringValue> factory;
    @Mock
    private Generator<StringValue> generator;
    @Mock
    private Function<StringValue, IntValue> function;

    @Test
    public void shouldReturnFactoryDependencies() {
        FieldRef dependency = new FieldRef(List.of(StringValue.of("key")), 0);
        List<FieldRef> dependencies = List.of(dependency);
        when(factory.getDependencies()).thenReturn(dependencies);
        MappingFactory<Integer, String, StringValue> mappingFactory = new MappingFactory<>(factory, function);

        List<FieldRef> result = mappingFactory.getDependencies();

        assertSame(dependencies, result, "Expected mappingFactory to return dependencies from nested factory");
    }

    @Test
    public void shouldCallGeneratorAndApplyFunctionOnResult() {
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
