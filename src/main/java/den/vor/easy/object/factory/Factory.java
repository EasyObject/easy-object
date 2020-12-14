package den.vor.easy.object.factory;

import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        MapValue globalParams = new MapValue(Map.of(StringValue.of("count"), IntValue.of(count)));
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
