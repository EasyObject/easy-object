package den.vor.easy.object.factory;

import java.util.stream.Stream;

public interface Generator<T> {

    T getNext(GenerationContext context);

    default boolean hasNext() {
        return true;
    }

    default Stream<T> stream(GenerationContext params) {
        return Stream.iterate(getNext(params), ignored -> hasNext(), ignored -> getNext(params));
    }
}
