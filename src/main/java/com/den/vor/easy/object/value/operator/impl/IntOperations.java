/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.operator.impl;

import com.den.vor.easy.object.value.impl.IntValue;
import com.den.vor.easy.object.value.operator.BinaryOperator;
import com.den.vor.easy.object.value.operator.BinaryOperatorImpl;
import com.den.vor.easy.object.value.impl.DoubleValue;
import com.den.vor.easy.object.value.impl.StringValue;

/**
 * Class that contains operator implementation for {@link IntValue}.
 */
public class IntOperations {

    /**
     * Binary addition operator implementation. Can calculate result when the second operand is:
     * * {@link Integer} - adds current value to the number. Returns {@link IntValue}.
     * * {@link Double} - adds current value to the number. Returns {@link DoubleValue}.
     * * {@link String} - converts current value to string and adds the second operand. Returns {@link StringValue}.
     */
    public static final BinaryOperator<Integer> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a + b)),
            BinaryOperatorImpl.operator(Double.class, (a, b) -> DoubleValue.of(a + b)),
            BinaryOperatorImpl.operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    /**
     * Binary subtraction operator implementation. Can calculate result when the second operand is:
     * * {@link Integer} - subtracts number from the current value. Returns {@link IntValue}.
     * * {@link Double} - subtracts number from the current value. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Integer> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a - b)),
            BinaryOperatorImpl.operator(Double.class, (a, b) -> DoubleValue.of(a - b))
    );

    /**
     * Binary multiplication operator implementation. Can calculate result when the second operand is:
     * * {@link Integer} - multiplies current value by the number. Returns {@link IntValue}.
     * * {@link Double} - multiplies current value by the number. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Integer> MULTIPLY_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a * b)),
            BinaryOperatorImpl.operator(Double.class, (a, b) -> DoubleValue.of(a * b))
    );

    /**
     * Binary division operator implementation. Can calculate result when the second operand is:
     * * {@link Integer} - divides current value by the number. Returns {@link IntValue}.
     * * {@link Double} - divides current value by the number. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Integer> DIVIDE_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a / b)),
            BinaryOperatorImpl.operator(Double.class, (a, b) -> DoubleValue.of(a / b))
    );

    /**
     * Binary power operator implementation. Can calculate result when the second operand is:
     * * {@link Integer} - elevates current value by the number. Returns {@link IntValue}.
     * * {@link Double} - elevates current value by the number. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Integer> POWER_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of((int) Math.pow(a, b))),
            BinaryOperatorImpl.operator(Double.class, (a, b) -> DoubleValue.of(Math.pow(a, b)))
    );

    /**
     * Binary power operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Remainder is equivalent to java '%' operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> REMAINDER_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a % b))
    );

    /**
     * Binary left shift operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Remainder is equivalent to java {@code <<} operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> LEFT_SHIFT_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a << b))
    );

    /**
     * Binary right shift operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Remainder is equivalent to java {@code >>} operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> RIGHT_SHIFT_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a >> b))
    );

    /**
     * Binary bitwise and operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Is equivalent to java {@code &} operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> BITWISE_AND_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a & b))
    );

    /**
     * Binary bitwise or operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Is equivalent to java '|' operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> BITWISE_OR_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a | b))
    );

    /**
     * Binary bitwise xor operator implementation. Can calculate result only when the second operand is {@link Integer}.
     * Is equivalent to java '^' operator.
     * Returns {@link IntValue}.
     */
    public static final BinaryOperator<Integer> BITWISE_XOR_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Integer.class, (a, b) -> IntValue.of(a ^ b))
    );

    private IntOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
