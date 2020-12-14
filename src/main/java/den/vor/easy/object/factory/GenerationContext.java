package den.vor.easy.object.factory;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomFactory;
import den.vor.easy.object.value.CompoundValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;

public class GenerationContext {

    private final CustomRandom random;
    private MapValue globalParams = MapValue.emptyMap();
    private Value<?> context = MapValue.emptyMap();
    private CompoundValue<?> parent = MapValue.emptyMap();

    public static GenerationContext fromParent(GenerationContext parentContext) {
        return new GenerationContext(parentContext.getRandom())
                .setGlobalParams(parentContext.getGlobalParams());
    }

    public GenerationContext() {
        this(RandomFactory.getRandom());
    }

    public GenerationContext(CustomRandom random) {
        this.random = random;
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
