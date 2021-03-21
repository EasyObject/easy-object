/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.consumer.Consumer;
import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Class that encapsulates a factory that is already configured. Is used to specify {@link Consumer}s.
 * @param <R> type of value generated by the encapsulated factory
 */
public class RootFactory<R extends Value<?>> {

    private final Factory<?, R> factory;
    private final int count;

    /**
     * Creates a new root factory instance.
     * @param factory factory to get generated values from
     * @param count number of values to be generated
     */
    public RootFactory(Factory<?, R> factory, int count) {
        this.factory = factory;
        this.count = count;
    }

    /**
     * Returns values generated by a {@link RootFactory#factory} as a stream, limited by {@link RootFactory#count}.
     * @return stream of generated values
     */
    public Stream<R> stream() {
        return factory.getGenerator().stream(new GenerationContext()).limit(count);
    }

    /**
     * Utility method that collects stream returned by {@link RootFactory#stream()} to a list.
     * @return list of generated objects
     */
    public List<R> getList() {
        return stream().collect(toList());
    }

    /**
     * Specifies a list of consumers for the factory.
     * @param consumers factory values consumers
     * @return {@link ConsumedFactory} instance
     */
    public ConsumedFactory<R> consume(Consumer<?>... consumers) {
        return new ConsumedFactory<>(this, List.of(consumers));
    }
}
