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
import org.apache.commons.rng.core.source64.XorShift1024Star;

import java.util.Random;

/**
 * Class that provides custom random with XorShift1024Star algorithm.
 */
public class XorShift1024StarProvider implements RandomProvider {

    public static final Random RANDOM = new Random();

    private static long[] generateSeed() {
        long[] seed = new long[16];
        for (int i = 0; i < 16; i++) {
            seed[i] = RANDOM.nextLong();
        }
        return seed;
    }

    @Override
    public CustomRandom getRandom() {
        return new Wrapper(generateSeed());
    }

    public CustomRandom getRandom(long[] seed) {
        return new Wrapper(seed);
    }

    /**
     * Wrapper for XorShift1024Star random.
     */
    private static class Wrapper extends XorShift1024Star implements CustomRandom {

        private final long[] seed;

        public Wrapper(long[] seed) {
            super(seed);
            this.seed = seed;
        }

        @Override
        public long[] getSeed() {
            return seed;
        }
    }
}
