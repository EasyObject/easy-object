/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.random.impl;


import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;

import java.util.SplittableRandom;

public class SplittableRandomProvider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new SplittableWrapper();
    }

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
        public float nextFloat() {
            return (float) splittableRandom.nextDouble();
        }

        @Override
        public double nextDouble() {
            return splittableRandom.nextDouble();
        }
    }
}
