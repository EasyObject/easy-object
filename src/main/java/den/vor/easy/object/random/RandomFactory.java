/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private RandomFactory() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
