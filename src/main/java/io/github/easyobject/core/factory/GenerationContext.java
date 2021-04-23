/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory;

import io.github.easyobject.core.random.CustomRandom;
import io.github.easyobject.core.random.RandomFactory;
import io.github.easyobject.core.value.CompoundValue;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.MapValue;

/**
 * Class that encapsulates generation context.
 */
public class GenerationContext {

    /**
     * Object that should be used as a source of randomness in all factories.
     */
    private final CustomRandom random;
    /**
     * Global generation parameters that do not vary among different generated objects.
     */
    private MapValue globalParams = MapValue.emptyMap();
    /**
     * Object that is currently under construction. Should be used to retrieve the dependency fields values.
     */
    private Value<?> context = MapValue.emptyMap();
    /**
     * Link to the parent {@link CompoundValue}. Is used to traverse the tree up.
     */
    private CompoundValue<?> parent = MapValue.emptyMap();

    public GenerationContext() {
        this(RandomFactory.getRandom());
    }

    public GenerationContext(CustomRandom random) {
        this.random = random;
    }

    public static GenerationContext fromParent(GenerationContext parentContext) {
        return new GenerationContext(parentContext.getRandom())
                .setGlobalParams(parentContext.getGlobalParams());
    }

    public MapValue getGlobalParams() {
        return globalParams;
    }

    public GenerationContext setGlobalParams(MapValue globalParams) {
        this.globalParams = globalParams;
        return this;
    }

    public Value<?> getContext() {
        return context;
    }

    public GenerationContext setContext(Value<?> context) {
        this.context = context;
        return this;
    }

    public CompoundValue<?> getParent() {
        return parent;
    }

    public GenerationContext setParent(CompoundValue<?> parent) {
        this.parent = parent;
        return this;
    }

    public CustomRandom getRandom() {
        return random;
    }
}
