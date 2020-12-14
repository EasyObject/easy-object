package den.vor.easy.object.fuzzy.util;

import den.vor.easy.object.random.DoubleWeightRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapWeightRandom<T> {

    private List<T> values;
    private DoubleWeightRandom doubleWeightRandom;

    public MapWeightRandom(Map<Double, T> map) {
        List<Double> weights = new ArrayList<>();
        this.values = new ArrayList<>();

        map.forEach((weight, value) -> {
            weights.add(weight);
            values.add(value);
        });

        doubleWeightRandom = new DoubleWeightRandom(weights);
    }

    public T getNext() {
        return values.get(doubleWeightRandom.getNext());
    }
}
