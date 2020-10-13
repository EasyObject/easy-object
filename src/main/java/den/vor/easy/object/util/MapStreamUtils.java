package den.vor.easy.object.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class MapStreamUtils {

    public static <T, R, K> Map<T, R> mapValues(Map<T, K> map, Function<K, R> valueFunction) {
        return map.entrySet().stream().collect(toMap(
            Entry::getKey,
            e -> valueFunction.apply(e.getValue())
        ));
    }
}
