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
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static den.vor.easy.object.value.impl.StringValue.of;
import static org.junit.jupiter.api.Assertions.*;

public class RoundRobinFactoryTest {

    @Test
    public void shouldReturnAllValuesOneByOneInCircle() {
        StringValue a = of("a");
        StringValue b = of("b");
        StringValue c = of("c");
        List<StringValue> values = List.of(a, b, c);
        RoundRobinFactory<String> factory = new RoundRobinFactory<>(values);
        Generator<Value<String>> generator = factory.getGenerator();
        GenerationContext context = new GenerationContext();
        assertEquals(a, generator.getNext(context), () -> "Expected factory to return " + a + " on call №1");
        assertEquals(b, generator.getNext(context), () -> "Expected factory to return " + b + " on call №2");
        assertEquals(c, generator.getNext(context), () -> "Expected factory to return " + c + " on call №3");
        assertEquals(a, generator.getNext(context), () -> "Expected factory to return " + a + " on call №4");
        assertEquals(b, generator.getNext(context), () -> "Expected factory to return " + b + " on call №5");
        assertEquals(c, generator.getNext(context), () -> "Expected factory to return " + c + " on call №6");
    }

    @Test
    public void shouldReturnEmptyDependenciesArray() {
        List<FieldRef> dependencies = new RoundRobinFactory<>(List.of(of("a"))).getDependencies();

        assertTrue(dependencies.isEmpty(), "Expected RoundRobin factory to have empty dependencies list");
    }
}
