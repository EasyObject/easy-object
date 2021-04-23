/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory.impl;

import com.den.vor.easy.object.factory.Generator;
import com.den.vor.easy.object.factory.LengthFactory;
import com.den.vor.easy.object.factory.ValueSource;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.ListValue;
import com.den.vor.easy.object.ref.FieldRef;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory that generates a list of values. Length of list is controlled by {@link LengthFactory#getLengthFactory()},
 * elements generated - by {@link ListFactory#elementFactory}
 *
 * @param <T> type of elements in the list
 */
public class ListFactory<T> extends LengthFactory<List<T>, ListValue<T>> {

    private ValueSource<T> elementFactory;

    public ListFactory(ValueSource<T> elementFactory,
                       ValueSource<Integer> lengthFactory) {
        super(lengthFactory);
        this.elementFactory = elementFactory;
    }

    @Override
    public Generator<ListValue<T>> getGenerator() {
        Generator<? extends Value<T>> generator = elementFactory.getGenerator();
        Generator<? extends Value<Integer>> lengthGenerator = getLengthFactory().getGenerator();
        return context -> {
            List<Value<T>> values = Stream.generate(() -> generator.getNext(context))
                    .limit(lengthGenerator.getNext(context).getValue())
                    .collect(Collectors.toList());
            return new ListValue<>(values);
        };
    }

    @Override
    protected List<FieldRef> getNestedFactoriesDependencies() {
        return elementFactory.getDependencies();
    }
}
