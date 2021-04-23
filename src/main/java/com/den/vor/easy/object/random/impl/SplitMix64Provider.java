/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.random.impl;

import com.den.vor.easy.object.random.RandomProvider;
import com.den.vor.easy.object.random.CustomRandom;
import org.apache.commons.rng.core.source64.SplitMix64;

/**
 * Class that provides custom random with SplitMix64 algorithm.
 */
public class SplitMix64Provider implements RandomProvider {

    @Override
    public CustomRandom getRandom() {
        return new Wrapper();
    }

    /**
     * Wrapper for SplitMix64 class.
     */
    private static class Wrapper extends SplitMix64 implements CustomRandom {

        public Wrapper() {
            super(System.nanoTime());
        }
    }
}
