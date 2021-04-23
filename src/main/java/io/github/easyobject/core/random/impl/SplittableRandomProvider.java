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

import java.util.SplittableRandom;

/**
 * Class that provides custom random with SplittableRandom algorithm.
 */
public class SplittableRandomProvider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new SplittableWrapper();
    }

    /**
     * Wrapper for SplittableRandom class.
     */
    private static class SplittableWrapper implements CustomRandom {

        private final SplittableRandom splittableRandom = new SplittableRandom();

        @Override
        public int nextInt() {
            return splittableRandom.nextInt();
        }

        @Override
        public int nextInt(int bound) {
            return splittableRandom.nextInt(bound);
        }

        @Override
        public long nextLong() {
            return splittableRandom.nextLong();
        }

        @Override
        public boolean nextBoolean() {
            return splittableRandom.nextBoolean();
        }

        @Override
        public double nextDouble() {
            return splittableRandom.nextDouble();
        }
    }
}
