/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionalValueTest {

    @Mock
    private BiFunction<Value<Object>, List<Value<?>>, Value<?>> biFunction;
    @Mock
    private Value<Object> context;
    @Mock
    private List<Value<?>> args;

    @Test
    void shouldApplyFunction_whenInvokeCalled() {
        FunctionalValue<Object> functionalValue = new FunctionalValue<>(biFunction);

        functionalValue.invoke(context, args);

        verify(biFunction, only()).apply(context, args);
        verifyNoInteractions(context, args);
    }

    @Test
    void shouldReturnIsIdempotentFlag() {
        FunctionalValue<Object> functionalValue = new FunctionalValue<>(biFunction, true);

        assertTrue(functionalValue.isIdempotent(), "Expected function to return idempotent flag=true");
        verifyNoInteractions(context, args, biFunction);
    }

}
