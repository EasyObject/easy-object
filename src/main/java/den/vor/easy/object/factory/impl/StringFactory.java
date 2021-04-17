/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.Generator;
import den.vor.easy.object.factory.LengthFactory;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.StringValue;

import java.util.Collections;
import java.util.List;

/**
 * Factory that generates strings from the provided characters.
 */
public class StringFactory extends LengthFactory<String, StringValue> {

    private char[] chars;

    /**
     * Creates a new factory instance.
     * @param chars characters that form a string
     * @param lengthFactory factory that will be used to generate string length
     */
    public StringFactory(char[] chars, Factory<Integer, ? extends Value<Integer>> lengthFactory) {
        super(lengthFactory);
        this.chars = chars;
    }

    public StringFactory(String chars, Factory<Integer, ? extends Value<Integer>> lengthFactory) {
        this(chars.toCharArray(), lengthFactory);
    }

    @Override
    public Generator<StringValue> getGenerator() {
        Generator<? extends Value<Integer>> lengthGenerator = getLengthFactory().getGenerator();
        return context -> {
            Integer length = lengthGenerator.getNext(context).getValue();
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(context.getRandom().next(chars));
            }
            return StringValue.of(stringBuilder.toString());
        };
    }

    @Override
    protected List<FieldRef> getNestedFactoriesDependencies() {
        return Collections.emptyList();
    }
}
