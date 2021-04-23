/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.consumer.formatter;

import io.github.easyobject.core.value.Value;

import java.util.List;

/**
 * Formatter for generated values. Basically transforms one or multiple {@link Value} into a new object
 *
 * @param <T> return type
 */
public interface Formatter<T> {

    T format(List<Value<?>> value);

    T format(Value<?> value);
}
