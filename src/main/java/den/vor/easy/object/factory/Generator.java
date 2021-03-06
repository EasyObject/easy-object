package den.vor.easy.object.factory;

import java.util.stream.Stream;

/**
 * Basic interface for objects that can generate other objects
 * @param <T> type of generated value
 */
public interface Generator<T> {

    /**
     * Generate a value
     * @param context context to use during the generation
     * @return generated value
     */
    T getNext(GenerationContext context);

    /**
     * Iterator-like method that can be used to determine cases when generator can't generate more values
     * @return flag that indicates whether generator can generate more values
     */
    default boolean hasNext() {
        return true;
    }

    /**
     * Creates a stream of values
     * @param context context to use during the generation
     * @return stream of generated value
     */
    default Stream<T> stream(GenerationContext context) {
        return Stream.iterate(getNext(context), ignored -> hasNext(), ignored -> getNext(context));
    }
}
