/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator;

import den.vor.easy.object.value.Value;

import java.util.List;

public interface Operator<T> {

    static <T> Operator<T> operator(OperatorImpl<T, ?> operator) {
        return new SingleOperator<>(operator);
    }

    @SafeVarargs
    static <T> Operator<T> operator(OperatorImpl<T, ?>... operators) {
        return new MultiOperator<>(operators);
    }

    Value<?> apply(T t, Value<?> value);


    class MultiOperator<T> implements Operator<T> {
        private final List<OperatorImpl<T, ?>> classToOperators;

        @SafeVarargs
        public MultiOperator(OperatorImpl<T, ?>... operators) {
            this.classToOperators = List.of(operators);
        }

        @Override
        public Value<?> apply(T t, Value<?> value) {
            Object right = value.getValue();
            for (OperatorImpl<T, ?> classToOperator : classToOperators) {
                if (classToOperator.getrClass().isInstance(right)) {
                    return classToOperator.apply(t, right);
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    class SingleOperator<T> implements Operator<T> {
        private final OperatorImpl<T, ?> operator;

        public SingleOperator(OperatorImpl<T, ?> operator) {
            this.operator = operator;
        }

        @Override
        public Value<?> apply(T t, Value<?> value) {
            Object right = value.getValue();
            if (operator.getrClass().isInstance(right)) {
                return operator.apply(t, right);
            }
            throw new UnsupportedOperationException();
        }
    }
}
