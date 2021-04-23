/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory.impl;

import com.den.vor.easy.object.factory.Factory;
import com.den.vor.easy.object.factory.Generator;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.ref.FieldRef;

import java.util.List;
import java.util.function.Function;

/**
 * Factory that applies a mapping function to a values of provided factory.
 * @param <T> specifies the return type of a mapping function ({@linkplain Value}).
 * @param <P> return type of the original factory
 */
public class MappingFactory<T, P extends Value<?>> extends Factory<T, Value<T>> {

    private Factory<?, P> factory;
    private Function<P, ? extends Value<T>> mappingFunction;

    public MappingFactory(Factory<?, P> factory,
                          Function<P, ? extends Value<T>> mappingFunction) {
        this.factory = factory;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public Generator<Value<T>> getGenerator() {
        Generator<P> generator = factory.getGenerator();
        return context -> mappingFunction.apply(generator.getNext(context));
    }

    @Override
    public List<FieldRef> getDependencies() {
        return factory.getDependencies();
    }
}
