/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.impl.BooleanValue;
import den.vor.easy.object.value.operator.BinaryOperator;

import static den.vor.easy.object.value.operator.BinaryOperatorImpl.operator;

/**
 * Class that contains operator implementation for {@link BooleanValue}.
 */
public class BooleanOperations {

    /**
     * Logical 'and' operator is valid when another operand is also boolean. Returns {@link BooleanValue}.
     Is equivalent to java boolean and-ing
     */
    public static final BinaryOperator<Boolean> AND_OPERATOR = BinaryOperator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a && b))
    );

    /**
     * Logical 'or' operator is valid when another operand is also boolean. Returns {@link BooleanValue}.
     * Is equivalent to java boolean or-ing
     */
    public static final BinaryOperator<Boolean> OR_OPERATOR = BinaryOperator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a || b))
    );

    /**
     * Logical 'xor' operator is valid when another operand is also boolean. Returns {@link BooleanValue}.
     * Is equivalent to java boolean xor-ing
     */
    public static final BinaryOperator<Boolean> XOR_OPERATOR = BinaryOperator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a ^ b))
    );

    private BooleanOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
