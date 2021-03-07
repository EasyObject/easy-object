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
