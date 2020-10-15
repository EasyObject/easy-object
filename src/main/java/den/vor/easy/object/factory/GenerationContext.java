package den.vor.easy.object.factory;

import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.RandomFactory;
import den.vor.easy.object.value.impl.MapValue;

public class GenerationContext {

    private CustomRandom random;
    private MapValue params;

    public GenerationContext() {
        this(RandomFactory.getRandom());
    }

    public GenerationContext(MapValue params) {
        this(RandomFactory.getRandom(), params);
    }

    public GenerationContext(CustomRandom random) {
        this(random, MapValue.emptyMap());
    }

    public GenerationContext(CustomRandom random, MapValue params) {
        this.random = random;
        this.params = params;
    }

    public CustomRandom getRandom() {
        return random;
    }

    public MapValue getParams() {
        return params;
    }
}
