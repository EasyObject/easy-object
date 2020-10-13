package den.vor.easy.object.random.impl;


import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;

public class SingletonRandomProvider implements RandomProvider {

    private final CustomRandom random;

    public SingletonRandomProvider(RandomProvider randomProvider) {
        this.random = randomProvider.getRandom();
    }

    public SingletonRandomProvider(CustomRandom random) {
        this.random = random;
    }

    @Override
    public CustomRandom getRandom() {
        return random;
    }
}
