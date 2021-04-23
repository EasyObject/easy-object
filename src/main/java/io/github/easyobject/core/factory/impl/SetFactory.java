/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.factory.impl;

import io.github.easyobject.core.factory.Generator;
import io.github.easyobject.core.factory.LengthFactory;
import io.github.easyobject.core.factory.ValueSource;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.SetValue;
import io.github.easyobject.core.exception.impl.UniqueElementsGenerationException;
import io.github.easyobject.core.ref.FieldRef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Factory that generates a set of values. Length of set is controlled by {@link LengthFactory#getLengthFactory()},
 * elements generated - by {@link SetFactory#elementFactory}.
 *
 * Due to the difficulty of generating unique values, factory generates up to {@code length * triesPerElement} values.
 * If it doesn't find enough unique values among them, it throws {@link UniqueElementsGenerationException}.
 *
 * @param <T> type of elements in the list
 */
public class SetFactory<T> extends LengthFactory<Set<T>, SetValue<T>> {

    public static final int DEFAULT_TRIES_PER_ELEMENT = 10;
    private ValueSource<T> elementFactory;
    private int triesPerElement;

    public SetFactory(ValueSource<T> elementFactory,
                      ValueSource<Integer> lengthFactory) {
        this(elementFactory, lengthFactory, DEFAULT_TRIES_PER_ELEMENT);
    }

    public SetFactory(ValueSource<T> elementFactory,
                      ValueSource<Integer> lengthFactory,
                      int triesPerElement) {
        super(lengthFactory);
        this.elementFactory = elementFactory;
        this.triesPerElement = triesPerElement;
    }

    @Override
    public Generator<SetValue<T>> getGenerator() {
        Generator<? extends Value<T>> generator = elementFactory.getGenerator();
        Generator<? extends Value<Integer>> lengthGenerator = getLengthFactory().getGenerator();
        return context -> {
            int length = lengthGenerator.getNext(context).getValue();
            Set<Value<T>> set = new HashSet<>();
            for (int i = 0; i < length * triesPerElement; i++) {
                set.add(generator.getNext(context));
                if (set.size() == length) {
                    return SetValue.of(set);
                }
            }
            throw new UniqueElementsGenerationException(length, set.size(), length * triesPerElement);
        };
    }

    @Override
    protected List<FieldRef> getNestedFactoriesDependencies() {
        return elementFactory.getDependencies();
    }
}
