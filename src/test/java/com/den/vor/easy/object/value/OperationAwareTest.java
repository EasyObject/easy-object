/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OperationAwareTest {

    @Mock
    private Value<?> value;

    @Spy
    private OperationAware operationAware;

    @Test
    public void shouldThrowUnsupportedOperation_whenBinaryPlusIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.plus(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenBinaryMinusIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.minus(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenMultiplyIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.multiply(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenDivideIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.divide(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenPowIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.pow(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenRemainderIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.remainder(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenShiftLeftIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.shiftLeft(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenShiftRightIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.shiftRight(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenLogicalAndIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.and(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenLogicalOrIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.or(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenLogicalXorIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.xor(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenBitwiseAndIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.bitwiseAnd(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenBitwiseOrIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.bitwiseOr(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenBitwiseXorIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.bitwiseXor(value));
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenUnaryMinusIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.minus());
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenUnaryPlusIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.plus());
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenUnaryNotIsCalled() {
        assertThrows(UnsupportedOperationException.class, () -> operationAware.not());
    }
}
