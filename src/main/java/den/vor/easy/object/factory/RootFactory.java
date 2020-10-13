package den.vor.easy.object.factory;

import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class RootFactory<T> {

    private final Factory<T> rootFactory;

    public RootFactory(Factory<T> rootFactory) {
        this.rootFactory = rootFactory;
    }

    public Stream<T> stream() {
        GenerationContext context = new GenerationContext();
        return rootFactory.getGenerator().stream(context);
    }

    public List<T> getList(int size) {
        return stream().limit(size).collect(toList());
    }
}
