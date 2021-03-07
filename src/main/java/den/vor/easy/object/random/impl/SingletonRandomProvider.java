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
