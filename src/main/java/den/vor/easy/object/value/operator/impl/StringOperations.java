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
import den.vor.easy.object.value.operator.Operator;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class StringOperations {

    public static final Operator<String> PLUS_OPERATOR = Operator.operator(
            operator(Object.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<String> MULTIPLY_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> StringValue.of(a.repeat(b)))
    );

    private StringOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
