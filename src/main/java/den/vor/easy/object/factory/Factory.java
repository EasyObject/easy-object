/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static den.vor.easy.object.factory.FactoryConstants.COUNT_STRING_VALUE;

public abstract class Factory<T, R extends Value<T>> {

    private boolean visible = true;

    public abstract Generator<R> getGenerator();

    public List<FieldRef> getDependencies() {
        return Collections.emptyList();
    }

    public boolean isVisible() {
        return visible;
    }

    public Factory<T, R> setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Factory<T, R> invisible() {
        return setVisible(false);
    }

    public RootFactory<R> prepare(int count) {
        MapValue globalParams = new MapValue(Map.of(COUNT_STRING_VALUE, IntValue.of(count)));
        PreparationContext context = new PreparationContext().setGlobalParams(globalParams);
        prepareInternal(context);
        return new RootFactory<>(this, count);
    }

    public RootFactory<R> prepare() {
        PreparationContext context = new PreparationContext();
        prepareInternal(context);
        return new RootFactory<>(this, Integer.MAX_VALUE);
    }

    protected void prepareInternal(PreparationContext preparationContext) {

    }
}
