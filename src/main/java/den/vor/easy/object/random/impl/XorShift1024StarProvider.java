package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;
import org.apache.commons.rng.core.source64.XorShift1024Star;

import java.util.Random;

public class XorShift1024StarProvider implements RandomProvider {

    public static final Random RANDOM = new Random();

    @Override
    public CustomRandom getRandom() {
        return new Wrapper(generateSeed());
    }

    public CustomRandom getRandom(long[] seed) {
        return new Wrapper(seed);
    }

    private static long[] generateSeed() {
        long[] seed = new long[16];
        for (int i = 0; i < 16; i++) {
            seed[i] = RANDOM.nextLong();
        }
        return seed;
    }

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
