/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.random;

/**
 * Interface for classes that can are basically producers of {@link CustomRandom}.
 */
public interface RandomProvider {

    /**
     * Create new or return existing instance of {@link CustomRandom}.
     * @return random instance
     */
    CustomRandom getRandom();
}
