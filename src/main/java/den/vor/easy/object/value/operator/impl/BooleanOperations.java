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
import den.vor.easy.object.value.operator.Operator;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class BooleanOperations {

    public static final Operator<Boolean> AND_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a && b))
    );

    public static final Operator<Boolean> OR_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a || b))
    );

    public static final Operator<Boolean> XOR_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a ^ b))
    );
}
