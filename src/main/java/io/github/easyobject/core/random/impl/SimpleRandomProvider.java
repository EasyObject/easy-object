/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.random.impl;


import io.github.easyobject.core.random.CustomRandom;
import io.github.easyobject.core.random.RandomProvider;

import java.util.Random;

/**
 * Class that provides custom random with {@link Random} implementation.
 */
public class SimpleRandomProvider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    /**
     * Wrapper for Random class.
     */
    private static class Wrapper extends Random implements CustomRandom {

    }
}
