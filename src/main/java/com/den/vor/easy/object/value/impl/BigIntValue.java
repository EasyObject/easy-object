/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.impl;

import com.den.vor.easy.object.value.NumberValue;

import java.math.BigInteger;

/**
 * Value that encapsulates {@link BigInteger}.
 * Does not support any arithmetical operations. Uses default comparison implementation.
 */
public class BigIntValue extends NumberValue<BigInteger> {

    private final BigInteger value;

    public BigIntValue(BigInteger value) {
        this.value = value;
    }

    /**
     * Wraps the given value with {@linkplain BigIntValue}.
     * @param value value to wrap
     */
    public static BigIntValue of(BigInteger value) {
        return new BigIntValue(value);
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return StringValue.of(String.valueOf(value));
    }

    @Override
    public String toString() {
        return "BigIntValue{" + value + '}';
    }
}
