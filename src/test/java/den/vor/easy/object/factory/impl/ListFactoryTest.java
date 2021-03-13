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
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.ListValue;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static den.vor.easy.object.facade.ValueFacade.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListFactoryTest {

    @Mock
    private Factory<String, Value<String>> elementFactory;
    @Mock
    private Factory<Integer, Value<Integer>> lengthFactory;
    @Mock
    private Generator<Value<String>> elementGenerator;
    @Mock
    private Generator<Value<Integer>> lengthGenerator;
    @Mock
    private StringValue firstString;
    @Mock
    private StringValue secondString;

    @Test
    void shouldCallElementFactoryGeneratorTimesReturnedByLengthFactory() {
        when(elementFactory.getGenerator()).thenReturn(elementGenerator);
        when(lengthFactory.getGenerator()).thenReturn(lengthGenerator);
        when(lengthGenerator.getNext(any(GenerationContext.class))).thenReturn(of(2));
        when(elementGenerator.getNext(any(GenerationContext.class))).thenReturn(firstString).thenReturn(secondString);

        ListFactory<String> listFactory = new ListFactory<>(elementFactory, lengthFactory);
        ListValue<String> result = listFactory.getGenerator().getNext(new GenerationContext());

        assertEquals(2, result.size(), () -> "Expected list=" + result + " to have size 2");
        assertEquals(result.get(of(0)), firstString, () -> "Expected " + firstString + " to be the first " +
                "element of list=" + result);
        assertEquals(result.get(of(1)), secondString, () -> "Expected " + secondString + " to be the second " +
                "element of list=" + result);
        verify(lengthFactory).getGenerator();
        verify(elementFactory).getGenerator();
        verify(lengthGenerator, only()).getNext(any(GenerationContext.class));
        verify(elementGenerator, times(2)).getNext(any(GenerationContext.class));
    }

    @Test
    void shouldReturnDependenciesFromElementAndLengthFactories() {
        FieldRef elementFirstDependency = new FieldRef(List.of(of("key1")), 1);
        FieldRef elementSecondDependency = new FieldRef(List.of(of("key2")), 0);
        FieldRef lengthFirstDependency = new FieldRef(List.of(of("key3")), 1);
        FieldRef lengthSecondDependency = new FieldRef(List.of(of("key4")), 0);

        when(elementFactory.getDependencies()).thenReturn(List.of(elementFirstDependency, elementSecondDependency));
        when(lengthFactory.getDependencies()).thenReturn(List.of(lengthFirstDependency, lengthSecondDependency));

        ListFactory<String> listFactory = new ListFactory<>(elementFactory, lengthFactory);
        List<FieldRef> dependencies = listFactory.getDependencies();


        assertEquals(3, dependencies.size(), () -> "Expected dependencies list=" + dependencies + " to have " +
                "size 3");
        assertTrue(dependencies.contains(lengthFirstDependency), () -> "Expected dependencies list=" + dependencies +
                " to contain length factory's dependency=" + lengthFirstDependency);
        assertTrue(dependencies.contains(lengthSecondDependency), () -> "Expected dependencies list=" + dependencies +
                " to contain length factory's dependency=" + lengthSecondDependency);
        Optional<FieldRef> elementsDependency = dependencies.stream()
                .filter(d -> d.getParentLinks() == 0 && d.getFirstPath().getValue().equals("key1"))
                .findFirst();
        assertTrue(elementsDependency.isPresent(), () -> "Expected dependencies list=" + dependencies +
                " to contain element's dependency with changed parentLink");
    }
}
