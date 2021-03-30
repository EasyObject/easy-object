/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.impl.StringValue;
import den.vor.easy.object.value.operator.BinaryOperator;

import static den.vor.easy.object.value.operator.BinaryOperatorImpl.operator;

/**
 * Class that contains operator implementation for {@link StringValue}.
 */
public class StringOperations {

    /**
     * Binary addition operator implementation. Works with the second operand of any type.
     * Converts the second operand to String and appends to first operand.
     * Returns {@link StringValue}.
     */
    public static final BinaryOperator<String> PLUS_OPERATOR = BinaryOperator.operator(
            operator(Object.class, (a, b) -> StringValue.of(a + b))
    );

    /**
     * Binary multiplication operator implementation.
     * Can calculate result only when the second operand is {@link Integer}.
     * Repeats the first operand times, specified by the second operand.
     * Returns {@link StringValue}.
     */
    public static final BinaryOperator<String> MULTIPLY_OPERATOR = BinaryOperator.operator(
            operator(Integer.class, (a, b) -> StringValue.of(a.repeat(b)))
    );

    private StringOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
