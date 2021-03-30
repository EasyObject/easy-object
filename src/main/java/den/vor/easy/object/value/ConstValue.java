/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

/**
 * Value that can encapsulate any object. Does not support any operations.
 * Due to performance reasons should be avoided if possible.
 * @param <T> type of encapsulated object.
 */
public class ConstValue<T> extends Value<T> {

    private final T value;

    public ConstValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
