/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

import den.vor.easy.object.value.impl.BooleanValue;

/**
 * Interface that should be implemented by all {@link Value} classes that support comparison in Expression Language.
 * All methods throw {@link UnsupportedOperationException} by default to make it easier implementing this interface.
 */
public interface ComparisonAware {

    /**
     * Equality comparison operator. EL code example: {@code 1 == 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue equalTo(Value<?> value) {
        return BooleanValue.of(equals(value));
    }

    /**
     * Non-equality comparison operator. EL code example: {@code 1 != 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue notEqualTo(Value<?> value) {
        return BooleanValue.of(!equals(value));
    }

    /**
     * Greater than comparison operator. EL code example: {@code 1 > 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue gt(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Greater than or equals comparison operator. EL code example: {@code 1 >= 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue gte(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Less than or equals comparison operator. EL code example: {@code 1 <= 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue lte(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Less than comparison operator. EL code example: {@code 1 < 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return comparison result
     */
    default BooleanValue lt(Value<?> value) {
        throw new UnsupportedOperationException();
    }
}
