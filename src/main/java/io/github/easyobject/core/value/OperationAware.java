/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value;

/**
 * Interface that should be implemented by all {@link Value} classes that support operations in Expression Language.
 * All methods throw {@link UnsupportedOperationException} by default to make it easier implementing this interface.
 */
public interface OperationAware {

    /**
     * Binary '+' operator. EL code example: {@code 1 + 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return addition result
     */
    default Value<?> plus(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary '-' operator. EL code example: {@code 1 - 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return subtraction result
     */
    default Value<?> minus(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary '*' operator. EL code example: {@code 1 * 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return multiplication result
     */
    default Value<?> multiply(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary '/' operator. EL code example: {@code 1 / 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return division result
     */
    default Value<?> divide(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary '**' operator. EL code example: {@code 2 ** 3}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return power result
     */
    default Value<?> pow(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary '%' operator. EL code example: {@code 1 % 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return remainder result
     */
    default Value<?> remainder(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary {@code <<} operator. EL code example: {@code 1 << 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return left shift result
     */
    default Value<?> shiftLeft(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Binary {@code >>} operator. EL code example: {@code 1 >> 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return right shift result
     */
    default Value<?> shiftRight(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Logical {@code &&} operator. EL code example: {@code true && false}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return logical and result
     */
    default Value<?> and(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Logical '||' operator. EL code example: {@code true || false}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return logical or result
     */
    default Value<?> or(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Logical '^' operator. EL code example: {@code true ^ false}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return logical exclusive or result
     */
    default Value<?> xor(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Bitwise {@code &} operator. EL code example: {@code 1 & 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return bitwise and result
     */
    default Value<?> bitwiseAnd(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Bitwise '|' operator. EL code example: {@code 1 | 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return bitwise or result
     */
    default Value<?> bitwiseOr(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Bitwise '^' operator. EL code example: {@code 1 ^ 2}.
     * This method is called on the left operand with the right operand as an argument.
     * @param value right operand
     * @return bitwise exclusive or result
     */
    default Value<?> bitwiseXor(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unary '-' operator. EL code example: {@code -2}.
     * @return negated result
     */
    default Value<?> minus() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unary '+' operator. EL code example: {@code +2}.
     * @return unary plus result
     */
    default Value<?> plus() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unary '!' operator. EL code example: {@code !true}.
     * @return inverter value
     */
    default Value<?> not() {
        throw new UnsupportedOperationException();
    }
}
