package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;
import org.apache.commons.rng.core.source64.XorShift1024Star;

import java.util.Random;

public class XorShift1024StarProvider implements RandomProvider {

    public static final Random RANDOM = new Random();

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static long[] getSeed() {
        long[] seed = new long[16];
        for (int i = 0; i < 16; i++) {
            seed[i] = RANDOM.nextLong();
        }
        return seed;
    }

    private static class Wrapper extends XorShift1024Star implements CustomRandom {

        public Wrapper() {
            super(getSeed());
        }
    }
}
