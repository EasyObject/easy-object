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

import java.util.function.BiFunction;

/**
 * Class that encapsulates logic of one binary operator for certain operand types.
 * @param <T> first operand type
 * @param <R> second operand type
 */
public class BinaryOperatorImpl<T, R> {
    private final Class<R> rClass;
    private final BiFunction<T, R, Value<?>> biFunction;

    public BinaryOperatorImpl(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        this.rClass = rClass;
        this.biFunction = biFunction;
    }

    public static <T, R> BinaryOperatorImpl<T, R> operator(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        return new BinaryOperatorImpl<>(rClass, biFunction);
    }

    @SuppressWarnings("unchecked")
    public Value<?> apply(T t, Object r) {
        return biFunction.apply(t, (R) r);
    }

    public Class<R> getRClass() {
        return rClass;
    }
}
