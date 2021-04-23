/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.random;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that provides wight random for integer values in the range [1, n].
 * If you need double weights, see {@link DoubleWeightRandom}.
 */
public final class IntWeightRandom {

    private final CustomRandom random = RandomFactory.getRandom();
    private final int totalWeight;
    private int[] accumulatedWeights;
    private boolean isSingleValue;

    /**
     * Create a weight random instance for integers with the corresponding integer weights from the list.
     * 0 - weights[0], 1 - weights[1] etc.
     * @param weights weights to set to numbers
     */
    public IntWeightRandom(List<Integer> weights) {
        if (weights.isEmpty()) {
            throw new IllegalArgumentException("Non-empty collection expected");
        }
        int size = weights.size();
        this.accumulatedWeights = new int[size];
        this.isSingleValue = size == 1;
        accumulatedWeights[0] = weights.get(0);
        for (int i = 1; i < weights.size(); ++i) {
            accumulatedWeights[i] = accumulatedWeights[i - 1] + weights.get(i);
        }
        totalWeight = accumulatedWeights[accumulatedWeights.length - 1];
    }

    /**
     * Create a weight random instance for integers using provided function to calculate their weights.
     * 0 gets weight function(0), 1 - function(1) etc.
     * @param weightFunc function that generates weights
     * @param count numbers to generate
     */
    public IntWeightRandom(IntFunction<Integer> weightFunc, int count) {
        this(IntStream.range(0, count).mapToObj(weightFunc).collect(Collectors.toList()));
    }

    /**
     * Returns a random integer in range [1, n]. n is specified on the object creation time.
     * Distribution is controlled by the number weights.
     * @return random integer
     */
    public int getNext() {
        if (isSingleValue) {
            return 0;
        }
        int generated = random.nextInt(totalWeight);
        if (generated < accumulatedWeights[0]) {
            return 0;
        }
        return process(0, accumulatedWeights.length - 1, generated);
    }

    private int process(int left, int right, int generated) {
        if (left + 1 == right) {
            return right;
        }
        int current = (left + right) >> 1;
        if (accumulatedWeights[current] <= generated) {
            if (accumulatedWeights[current + 1] > generated) {
                return current + 1;
            }
            return process(current, right, generated);
        }
        return process(left, current, generated);
    }
}
