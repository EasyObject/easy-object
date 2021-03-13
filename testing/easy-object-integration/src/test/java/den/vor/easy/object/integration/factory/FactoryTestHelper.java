/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.factory;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.value.Value;

import java.util.function.Consumer;

public class FactoryTestHelper<T, R extends Value<T>> {

    public static final int OBJECTS_TESTED = 1000;
    private Factory<T, R> factory;
    private Consumer<T> assertionRunner;

    public static <T, R extends Value<T>> FactoryTestHelper<T, R> of(Factory<T, R> factory) {
        return new FactoryTestHelper<T, R>().withFactory(factory);
    }

    public FactoryTestHelper<T, R> withFactory(Factory<T, R> factory) {
        this.factory = factory;
        return this;
    }

    public FactoryTestHelper<T, R> withAssertionRunner(Consumer<T> assertionRunner) {
        this.assertionRunner = assertionRunner;
        return this;
    }

    public void test() {
        factory.prepare(OBJECTS_TESTED)
                .stream()
                .limit(OBJECTS_TESTED)
                .map(Value::getValue)
                .forEach(assertionRunner);
    }
}
