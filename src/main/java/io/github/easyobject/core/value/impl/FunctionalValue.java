/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.Value;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Represents a callable value.
 * @param <T> type of expected context
 */
public class FunctionalValue<T> extends Value<Object> {

    /**
     * Function that is used to calculate the result.
     * First argument - invocation context, value on which method is called. For functions is expected to be null.
     * Second argument - list of method arguments.
     */
    private final BiFunction<Value<T>, List<Value<?>>, Value<?>> function;

    /**
     * Flag that indicates whether function result may vary between invocations with same arguments.
     * Idempotent functions call may be calculated on the compile time.
     */
    private boolean idempotent;

    public FunctionalValue(BiFunction<Value<T>, List<Value<?>>, Value<?>> function) {
        this.function = function;
    }

    public FunctionalValue(BiFunction<Value<T>, List<Value<?>>, Value<?>> function, boolean idempotent) {
        this.function = function;
        this.idempotent = idempotent;
    }

    @SuppressWarnings("unchecked")
    public Value<?> invoke(Value<?> context, List<Value<?>> args) {
        return function.apply((Value<T>) context, args);
    }

    @Override
    public Object getValue() {
        return null;
    }

    public boolean isIdempotent() {
        return idempotent;
    }
}
