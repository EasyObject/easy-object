package den.vor.easy.object.random.impl;


import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomProvider;

import java.util.Random;

public class SimpleRandomProvider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    private static class Wrapper extends Random implements CustomRandom {

    }
}
