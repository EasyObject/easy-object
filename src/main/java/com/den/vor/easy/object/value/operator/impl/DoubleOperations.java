/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.operator.impl;

import com.den.vor.easy.object.value.operator.BinaryOperator;
import com.den.vor.easy.object.value.operator.BinaryOperatorImpl;
import com.den.vor.easy.object.value.impl.DoubleValue;
import com.den.vor.easy.object.value.impl.StringValue;

/**
 * Class that contains operator implementation for {@link DoubleValue}.
 */
public class DoubleOperations {

    /**
     * Binary addition operator implementation. Can calculate result when the second operand is:
     * * {@link Number} - adds current value to number's {@link Number#doubleValue()}. Returns {@link DoubleValue}.
     * * {@link String} - converts current value to string and adds the second operand. Returns {@link StringValue}.
     */
    public static final BinaryOperator<Double> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Number.class, (a, b) -> DoubleValue.of(a + b.doubleValue())),
            BinaryOperatorImpl.operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    /**
     * Binary subtraction operator implementation.
     * Can calculate result only when the second operand is {@link Number}.
     * Subtract number's {@link Number#doubleValue()} from current value. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Double> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Number.class, (a, b) -> DoubleValue.of(a - b.doubleValue()))
    );

    /**
     * Binary multiplication operator implementation.
     * Can calculate result only when the second operand is {@link Number}.
     * Multiplies number's {@link Number#doubleValue()} by current value. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Double> MULTIPLY_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Number.class, (a, b) -> DoubleValue.of(a * b.doubleValue()))
    );

    /**
     * Binary division operator implementation.
     * Can calculate result only when the second operand is {@link Number}.
     * Divides current value by number's {@link Number#doubleValue()}. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Double> DIVIDE_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Number.class, (a, b) -> DoubleValue.of(a / b.doubleValue()))
    );

    /**
     * Binary power operator implementation.
     * Can calculate result only when the second operand is {@link Number}.
     * Elevates current value by number's {@link Number#doubleValue()}. Returns {@link DoubleValue}.
     */
    public static final BinaryOperator<Double> POWER_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Number.class, (a, b) -> DoubleValue.of(Math.pow(a, b.doubleValue())))
    );

    private DoubleOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
