/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

public interface OperationAware {

    default Value<?> plus(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> minus(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> multiply(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> divide(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> pow(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> remainder(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> shiftLeft(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> shiftRight(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> and(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> or(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> xor(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> bitwiseAnd(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> bitwiseOr(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> bitwiseXor(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default Value<?> minus() {
        throw new UnsupportedOperationException();
    }

    default Value<?> plus() {
        throw new UnsupportedOperationException();
    }

    default Value<?> not() {
        throw new UnsupportedOperationException();
    }
}
