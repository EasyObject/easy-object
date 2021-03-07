/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomFactory;
import den.vor.easy.object.value.impl.MapValue;

public class PreparationContext {

    private final CustomRandom random;
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
