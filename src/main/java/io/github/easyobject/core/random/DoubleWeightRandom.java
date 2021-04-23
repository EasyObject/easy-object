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
 * Weights are configured as double numbers.
 * If you need integer weights, see {@link IntWeightRandom}.
 */
public final class DoubleWeightRandom {

    private final CustomRandom random = RandomFactory.getRandom();
    private final double lastWeight;
    private final int lastElement;
    private double[] accumulatedWeights;
    private boolean isSingleValue;

    /**
     * Create a weight random instance for integers with the corresponding double weights from the list.
     * 0 - weights[0], 1 - weights[1] etc.
     * @param weights weights to set to numbers
     */
    public DoubleWeightRandom(List<? extends Number> weights) {
        if (weights.isEmpty()) {
            throw new IllegalArgumentException("Non-empty collection expected");
        }
        int size = weights.size();
        this.accumulatedWeights = new double[size];
        this.isSingleValue = size == 1;
        initializeWeights(weights, size);
        lastElement = accumulatedWeights.length - 1;
        lastWeight = accumulatedWeights[lastElement];
    }

    /**
     * Create a weight random instance for integers using provided function to calculate their weights.
     * 0 gets weight function(0), 1 - function(1) etc.
     * @param weightFunc function that generates weights
     * @param count numbers to generate
     */
    public DoubleWeightRandom(IntFunction<? extends Number> weightFunc, int count) {
        this(IntStream.range(1, count + 1).mapToObj(weightFunc).collect(Collectors.toList()));
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

        double generated = random.nextDouble();

        return generated > lastWeight ?
                lastElement :
                process(0, lastElement, generated);
    }

    private int process(int left, int right, double generated) {
        if (left + 1 == right) {
            return left;
        }
        int current = (left + right) / 2;
        if (accumulatedWeights[current] < generated) {
            if (accumulatedWeights[current + 1] >= generated) {
                return current;
            }
            return process(current, right, generated);
        } else {
            return process(left, current, generated);
        }
    }

    private void initializeWeights(List<? extends Number> weights, int size) {
        double sum = weights.get(size - 1).doubleValue();
        for (int i = 1; i < weights.size(); ++i) {
            double current = weights.get(i - 1).doubleValue();
            accumulatedWeights[i] = accumulatedWeights[i - 1] + current;
            sum += current;
        }
        for (int i = 0; i < accumulatedWeights.length; ++i) {
            accumulatedWeights[i] /= sum;
        }
    }
}
