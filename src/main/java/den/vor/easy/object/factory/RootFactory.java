package den.vor.easy.object.factory;

import den.vor.easy.object.consumer.Consumer;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;
import static java.util.stream.Collectors.toList;

public class RootFactory<R extends Value<?>> {

    private final Factory<?, R> rootFactory;
    private final int count;

    public RootFactory(Factory<?, R> rootFactory, int count) {
        this.rootFactory = rootFactory;
        this.count = count;
    }

    public Stream<R> stream() {
        return getStream(new GenerationContext()).limit(count);
    }

    private Stream<R> getStream(GenerationContext context) {
        return rootFactory.getGenerator().stream(context);
    }

    public List<R> getList(int size) {
        return stream().limit(size).collect(toList());
    }

    public ConsumedFactory<R> consume(Consumer<?>... consumers) {
        return new ConsumedFactory<>(this, List.of(consumers));
    }
}
