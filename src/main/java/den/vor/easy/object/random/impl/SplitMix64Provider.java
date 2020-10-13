package den.vor.easy.object.random.impl;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;
import org.apache.commons.rng.core.source64.SplitMix64;

public class SplitMix64Provider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static class Wrapper extends SplitMix64 implements CustomRandom {

        public Wrapper() {
            super(System.nanoTime());
        }
    }
}
