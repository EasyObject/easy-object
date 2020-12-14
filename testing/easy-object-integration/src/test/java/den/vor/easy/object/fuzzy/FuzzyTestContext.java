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
