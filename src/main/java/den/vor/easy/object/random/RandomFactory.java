package den.vor.easy.object.random;

import den.vor.easy.object.random.impl.XorShift1024StarProvider;

public class RandomFactory {

    private static RandomProvider RANDOM_PROVIDER = new XorShift1024StarProvider();

    public static void setRandomProvider(RandomProvider randomProvider) {
        RANDOM_PROVIDER = randomProvider;
    }

    public static CustomRandom getRandom() {
        return RANDOM_PROVIDER.getRandom();
    }
}
