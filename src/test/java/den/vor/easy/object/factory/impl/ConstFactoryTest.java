package den.vor.easy.object.factory.impl;


import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.Generator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

public class ConstFactoryTest {

    @Test
    public void shouldReturnTheSameObject() {
        Object value = new Object();

        ConstFactory<Object> constFactory = new ConstFactory<>(value);
        Generator<Object> generator = constFactory.getGenerator();

        assertThat(generator.getNext(new GenerationContext()), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()), sameInstance(value));
        assertThat(generator.getNext(new GenerationContext()), sameInstance(value));
    }
}