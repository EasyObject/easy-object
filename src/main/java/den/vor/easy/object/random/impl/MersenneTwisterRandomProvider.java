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
import org.apache.commons.rng.core.source32.MersenneTwister;

import java.util.Random;

public class MersenneTwisterRandomProvider implements RandomProvider {

    public static final Random RANDOM = new Random();

    private static int[] getSeed() {
        int[] seed = new int[16];
        for (int i = 0; i < 16; i++) {
            seed[i] = RANDOM.nextInt();
        }
        return seed;
    }

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static class Wrapper extends MersenneTwister implements CustomRandom {

        public Wrapper() {
            super(MersenneTwisterRandomProvider.getSeed());
        }
    }
}
