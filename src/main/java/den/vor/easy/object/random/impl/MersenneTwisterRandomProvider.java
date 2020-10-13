package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;
import org.apache.commons.rng.core.source32.MersenneTwister;

import java.util.Random;

public class MersenneTwisterRandomProvider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static int[] getSeed() {
        int[] seed = new int[16];
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            seed[i] = random.nextInt();
        }
        return seed;
    }

    private static class Wrapper extends MersenneTwister implements CustomRandom {

        public Wrapper() {
            super(getSeed());
        }
    }
}
