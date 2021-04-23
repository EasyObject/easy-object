/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.operator;

import io.github.easyobject.core.value.Value;

import java.util.List;

/**
 * Registry of overloaded operator implementations for a constant left operand type.
 * (This class may encapsulate functions for int+double, int+string, int+int, ...).
 * @param <T> left operand type
 */
public interface BinaryOperator<T> {

    static <T> BinaryOperator<T> operator(BinaryOperatorImpl<T, ?> operator) {
        return new SingleBinaryOperator<>(operator);
    }

    @SafeVarargs
    static <T> BinaryOperator<T> operator(BinaryOperatorImpl<T, ?>... operators) {
        return new MultiBinaryOperator<>(operators);
    }

    /**
     * Apply operator.
     * @param t left operand
     * @param value right operand, wrapped in {@link Value}
     * @return operation result
     */
    Value<?> apply(T t, Value<?> value);

    /**
     * {@linkplain BinaryOperator} that stores multiple overloaded operator implementations.
     * Is created by {@link BinaryOperator#operator(BinaryOperatorImpl[])}.
     * @param <T> left operand type
     */
    class MultiBinaryOperator<T> implements BinaryOperator<T> {
        private final List<BinaryOperatorImpl<T, ?>> classToOperators;

        @SafeVarargs
        public MultiBinaryOperator(BinaryOperatorImpl<T, ?>... operators) {
            this.classToOperators = List.of(operators);
        }

        @Override
        public Value<?> apply(T t, Value<?> value) {
            Object right = value.getValue();
            for (BinaryOperatorImpl<T, ?> classToOperator : classToOperators) {
                if (classToOperator.getRClass().isInstance(right)) {
                    return classToOperator.apply(t, right);
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    /**
     * {@linkplain BinaryOperator} that stores a single operator implementations.
     * Doesn't hold any collections and therefore has better performance than {@link MultiBinaryOperator}.
     * Is created by {@link BinaryOperator#operator(BinaryOperatorImpl)}.
     * @param <T> left operand type
     */
    class SingleBinaryOperator<T> implements BinaryOperator<T> {
        private final BinaryOperatorImpl<T, ?> operator;

        public SingleBinaryOperator(BinaryOperatorImpl<T, ?> operator) {
            this.operator = operator;
        }

        @Override
        public Value<?> apply(T t, Value<?> value) {
            Object right = value.getValue();
            if (operator.getRClass().isInstance(right)) {
                return operator.apply(t, right);
            }
            throw new UnsupportedOperationException();
        }
    }
}
