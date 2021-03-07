/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.impl;

import den.vor.easy.object.factory.ComparableFactory;
import den.vor.easy.object.factory.GenerationContext;
import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.IntValue;

/**
 * Factory, which {@link den.vor.easy.object.factory.Generator} returns 4-byte signed numbers.
 */
public class IntFactory extends ComparableFactory<Integer, IntValue> {

    public IntFactory() {
        this(-1_000_000, 1_000_000);
    }

    /**
     * Creates a new factory instance.
     *
     * @param min lower inclusive bound for returned values
     * @param max upper inclusive bound for returned values
     */
    public IntFactory(int min, int max) {
        super(min, max);
    }

    @Override
    protected IntValue getBetween(GenerationContext context, Bound<Integer> min, Bound<Integer> max) {
        int minBound = min.isInclusive() ? min.getValue() : min.getValue() + 1;
        int maxBound = max.isInclusive() ? max.getValue() : max.getValue() - 1;
        if ((long) maxBound - minBound > Integer.MAX_VALUE) {
            // This can be the case when generating any int
            int value = (int) context.getRandom().nextLongInclusive(minBound, maxBound);
            return IntValue.of(value);
        }
        return IntValue.of(context.getRandom().nextIntInclusive(minBound, maxBound));
    }

    @Override
    protected Integer cast(Value<?> value) {
        return value.as(Integer.class);
    }
}
