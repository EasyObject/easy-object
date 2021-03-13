/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.enums.impl;

import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static den.vor.easy.object.value.impl.StringValue.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionEnumValuesProviderTest {

    @Test
    void shouldReturnRandomCollectionElement(@Mock CustomRandom random) {
        StringValue b = of("b");
        List<StringValue> values = List.of(of("a"), b, of("c"));

        when(random.next(values)).then(invocation -> {
            List<?> argument = invocation.getArgument(0);
            return argument.get(1);
        });

        GenerationContext context = new GenerationContext(random);

        CollectionEnumValuesProvider<StringValue> provider = new CollectionEnumValuesProvider<>(values);
        StringValue actual = provider.getNext(context);

        assertEquals(b, actual, () -> "Expected provider to return " + b + ", got " + actual);
        verify(random, only()).next(values);
    }
}
