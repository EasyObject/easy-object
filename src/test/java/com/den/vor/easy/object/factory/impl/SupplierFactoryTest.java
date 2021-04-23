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
import com.den.vor.easy.object.value.impl.IntValue;
import com.den.vor.easy.object.factory.GenerationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierFactoryTest {

    @Mock
    private GenerationContext generationContext;
    @Mock
    private Supplier<IntValue> supplier;
    @Mock
    private IntValue firstResult;
    @Mock
    private IntValue secondResult;


    @Test
    void shouldCallSupplierAndReturnItsValue_whenSupplierCalled() {
        when(supplier.get()).thenReturn(firstResult).thenReturn(secondResult);

        SupplierFactory<Integer, IntValue> factory = new SupplierFactory<>(supplier);
        Generator<IntValue> generator = factory.getGenerator();

        IntValue result = generator.getNext(generationContext);
        assertSame(result, firstResult, () -> "Expected supplier factory to return " + firstResult +
                " on the first call");
        verify(supplier).get();

        result = generator.getNext(generationContext);
        assertSame(result, secondResult, () -> "Expected supplier factory to return " + secondResult +
                " on the second call");
        verify(supplier, times(2)).get();

        verifyNoInteractions(generationContext, firstResult, secondResult);
    }
}
