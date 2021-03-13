/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

public class FuzzyTestContext {

    private MapValue globalParams;
    private Value<?> context;

    public MapValue getGlobalParams() {
        return globalParams;
    }

    public FuzzyTestContext setGlobalParams(MapValue globalParams) {
        this.globalParams = globalParams;
        return this;
    }

    public Value<?> getContext() {
        return context;
    }

    public FuzzyTestContext setContext(Value<?> context) {
        this.context = context;
        return this;
    }
}
