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
import den.vor.easy.object.value.impl.StringValue;
import den.vor.easy.object.value.operator.Operator;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class DoubleOperations {

    public static final Operator<Double> PLUS_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a + b.doubleValue())),
            operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<Double> MINUS_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a - b.doubleValue()))
    );

    public static final Operator<Double> MULTIPLY_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a * b.doubleValue()))
    );

    public static final Operator<Double> DIVIDE_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a / b.doubleValue()))
    );

    public static final Operator<Double> POWER_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(Math.pow(a, b.doubleValue())))
    );

    private DoubleOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
