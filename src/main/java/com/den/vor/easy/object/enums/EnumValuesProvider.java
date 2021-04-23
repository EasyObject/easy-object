/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.enums;

import com.den.vor.easy.object.factory.GenerationContext;

/**
 * Interface for objects that randomly select an object from a relatively small set.
 *
 * @param <T> type of object selected
 */
public interface EnumValuesProvider<T> {

    T getNext(GenerationContext context);
}
