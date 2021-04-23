/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.random.impl;


import io.github.easyobject.core.random.RandomProvider;
import io.github.easyobject.core.random.CustomRandom;

/**
 * Random provider that encapsulates a custom random and always returns it.
 */
public class SingletonRandomProvider implements RandomProvider {

    private final CustomRandom random;

    /**
     * Creates a new singleton random provider instance.
     * Calls {@link RandomProvider#getRandom()} and saves the result.
     * @param randomProvider provider to get custom random from
     */
    public SingletonRandomProvider(RandomProvider randomProvider) {
        this.random = randomProvider.getRandom();
    }

    /**
     * Creates a new singleton random provider instance.
     * @param random random instance to save
     */
    public SingletonRandomProvider(CustomRandom random) {
        this.random = random;
    }

    @Override
    public CustomRandom getRandom() {
        return random;
    }
}
