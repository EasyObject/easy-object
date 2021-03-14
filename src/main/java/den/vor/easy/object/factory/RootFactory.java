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

public class RootFactory<R extends Value<?>> {

    private final Factory<?, R> factory;
    private final int count;

    public RootFactory(Factory<?, R> factory, int count) {
        this.factory = factory;
        this.count = count;
    }

    public Stream<R> stream() {
        return getStream(new GenerationContext()).limit(count);
    }

    private Stream<R> getStream(GenerationContext context) {
        return factory.getGenerator().stream(context);
    }

    public List<R> getList(int size) {
        return stream().limit(size).collect(toList());
    }

    public ConsumedFactory<R> consume(Consumer<?>... consumers) {
        return new ConsumedFactory<>(this, List.of(consumers));
    }
}
