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

public class OperatorImpl<T, R> {
    private final Class<R> rClass;
    private final BiFunction<T, R, Value<?>> biFunction;

    public OperatorImpl(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        this.rClass = rClass;
        this.biFunction = biFunction;
    }

    public static <T, R> OperatorImpl<T, R> operator(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        return new OperatorImpl<>(rClass, biFunction);
    }

    @SuppressWarnings("unchecked")
    public Value<?> apply(T t, Object r) {
        return biFunction.apply(t, (R) r);
    }

    public Class<R> getrClass() {
        return rClass;
    }
}
