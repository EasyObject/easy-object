package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;
import org.apache.commons.rng.core.source32.MersenneTwister;

import java.util.Random;

public class MersenneTwisterRandomProvider implements RandomProvider {

    public static final Random RANDOM = new Random();

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static int[] getSeed() {
        int[] seed = new int[16];
        for (int i = 0; i < 16; i++) {
            seed[i] = RANDOM.nextInt();
        }
        return seed;
    }

    private static class Wrapper extends MersenneTwister implements CustomRandom {

        public Wrapper() {
            super(getSeed());
        }
    }
}
