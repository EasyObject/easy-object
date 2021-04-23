/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory;

import com.den.vor.easy.object.value.impl.MapValue;
import com.den.vor.easy.object.random.CustomRandom;
import com.den.vor.easy.object.random.RandomFactory;

/**
 * Encapsulates the state that is used during the {@link Factory#prepare()} and {@link Factory#prepare(int)} methods.
 */
public class PreparationContext {

    /**
     * Object that should be used as a source of randomness during the factory preparation.
     */
    private final CustomRandom random;
    /**
     * Global generation parameters that do not vary among different generated objects.
     */
    private MapValue globalParams = MapValue.emptyMap();

    public PreparationContext() {
        this(RandomFactory.getRandom());
    }

    public PreparationContext(CustomRandom random) {
        this.random = random;
    }

    public MapValue getGlobalParams() {
        return globalParams;
    }

    public PreparationContext setGlobalParams(MapValue globalParams) {
        this.globalParams = globalParams;
        return this;
    }

    public CustomRandom getRandom() {
        return random;
    }
}
