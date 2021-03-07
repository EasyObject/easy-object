/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;
import den.vor.easy.object.value.operator.Operator;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class IntOperations {

    public static final Operator<Integer> PLUS_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a + b)),
            operator(Double.class, (a, b) -> DoubleValue.of(a + b)),
            operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<Integer> MINUS_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a - b)),
            operator(Double.class, (a, b) -> DoubleValue.of(a - b))
    );

    public static final Operator<Integer> MULTIPLY_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a * b)),
            operator(Double.class, (a, b) -> DoubleValue.of(a * b))
    );

    public static final Operator<Integer> DIVIDE_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a / b)),
            operator(Double.class, (a, b) -> DoubleValue.of(a / b))
    );

    public static final Operator<Integer> POWER_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of((int) Math.pow(a, b))),
            operator(Double.class, (a, b) -> DoubleValue.of(Math.pow(a, b)))
    );

    public static final Operator<Integer> REMAINDER_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a % b))
    );

    public static final Operator<Integer> LEFT_SHIFT_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a << b))
    );

    public static final Operator<Integer> RIGHT_SHIFT_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a >> b))
    );

    public static final Operator<Integer> BITWISE_AND_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a & b))
    );

    public static final Operator<Integer> BITWISE_OR_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a | b))
    );

    public static final Operator<Integer> BITWISE_XOR_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> IntValue.of(a ^ b))
    );
}
