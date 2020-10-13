package den.vor.easy.object.factory;

import den.vor.easy.object.util.Graph;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.ref.FieldRef;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public abstract class CompoundFactory<T> extends Factory<T> {

    protected abstract Map<? extends ScalarValue<?>, Factory<?>> getChildFactories();

    @Override
    public List<FieldRef> getDependencies() {
        return getChildFactories().values().stream()
                .map(Factory::getDependencies)
                .flatMap(Collection::stream)
                .filter(ref -> ref.getParentLinks() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public Generator<T> getGenerator() {
        return doGetGenerator(getOrderedGenerators());
    }

    protected abstract Generator<T> doGetGenerator(List<ScalarValue<?>> orderedKeys);

    protected List<ScalarValue<?>> getOrderedGenerators() {
        Map<ScalarValue<?>, List<ScalarValue<?>>> childFactoriesDependencies = getChildFactories().entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().getDependencies().stream()
                        .filter(d -> d.getParentLinks() == 0)
                        .map(FieldRef::getFirstPath)
                        .collect(toList())));
        Graph<ScalarValue<?>> graph = new Graph<>(childFactoriesDependencies);
        return graph.getCreationOrder();
    }
}
