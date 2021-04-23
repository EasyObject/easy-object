/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.random;


import java.util.List;

/**
 * Interface for objects that can generate basic pseudo-random values.
 */
public interface CustomRandom {

    /**
     * Generates an uniformly distributed integer in range [Integer.MIN_VALUE, Integer.MAX_VALUE).
     * For bounded values see {@link CustomRandom#nextInt(int)}, {@link CustomRandom#nextInt(int, int)},
     * {@link CustomRandom#nextIntInclusive(int, int)}.
     * @return random int
     */
    int nextInt();

    /**
     * Generates an uniformly distributed integer in range [Integer.MIN_VALUE, Integer.MAX_VALUE).
     * @return random int
     */
    int nextInt(int bound);

    /**
     * Generates an uniformly distributed long in range [Long.MIN_VALUE, Long.MAX_VALUE).
     * For bounded values see {@link CustomRandom#nextLong(long, long)}.
     * @return random long
     */
    long nextLong();

    /**
     * Generates a boolean with 0.5 change of returning true.
     * If you need to specify the probability, see {@linkplain CustomRandom#nextBoolean(double)}.
     * @return random boolean
     */
    boolean nextBoolean();

    /**
     * Generates a double uniformly distributed in range [0, 1).
     * If you need to specify bounds, see {@link CustomRandom#nextDouble(double, double)}.
     * @return random double
     */
    double nextDouble();

    /**
     * Generates a double uniformly distributed in range [min, max).
     * If you need to generate values in range [0, 1), see {@link CustomRandom#nextDouble()}.
     * @return random double
     */
    default double nextDouble(double min, double max) {
        return (max - min) * nextDouble() + min;
    }

    /**
     * Generates an uniformly distributed long in range [min, max).
     * For unbounded values see {@link CustomRandom#nextLong()}.
     * @return random long
     */
    default long nextLong(long min, long max) {
        return min + (long) (nextDouble() * (max - min));
    }

    /**
     * Generates an uniformly distributed long in range [min, max].
     * For unbounded values see {@link CustomRandom#nextLong()}.
     * @return random long
     */
    default long nextLongInclusive(long min, long max) {
        return nextLong(min, max + 1);
    }

    /**
     * Generates an uniformly distributed int in range [min, max).
     * <b>{@code max - min} must not overflow int, use {@linkplain CustomRandom#nextLong(long, long)} in such cases</b>
     * For unbounded values see {@link CustomRandom#nextLong()}.
     * @return random int
     */
    default int nextInt(int min, int max) {
        return nextInt(max - min) + min;
    }

    /**
     * Generates an uniformly distributed int in range [min, max].
     * <b>{@code max+1-min} must not overflow int, use {@linkplain CustomRandom#nextLong(long, long)} in such cases</b>
     * For unbounded values see {@link CustomRandom#nextLong()}.
     * @return random int
     */
    default int nextIntInclusive(int min, int max) {
        return nextInt(min, max + 1);
    }

    /**
     * Generates a boolean.
     * Argument specifies a probability of returning {@code true} therefore must be in range [0, 1].
     * If you need a 0.5 probability, see {@linkplain CustomRandom#nextBoolean()}.
     * @return random boolean
     */
    default boolean nextBoolean(double chances) {
        if (chances > 1 || chances < 0) {
            throw new IllegalArgumentException("Chances should be between 0 and 1");
        }
        return nextDouble() < chances;
    }

    /**
     * Returns a random value from the list with an uniform distribution.
     * @return random list member
     */
    default <T> T next(List<T> list) {
        return list.get(nextInt(list.size()));
    }

    /**
     * Returns a random value from the array with an uniform distribution.
     * @return random array member
     */
    default <T> T next(T[] array) {
        return array[nextInt(array.length)];
    }

    /**
     * Returns a random character from the array with an uniform distribution.
     * @return random array member
     */
    default char next(char[] array) {
        return array[nextInt(array.length)];
    }

    /**
     * Populates an array with random bytes.
     * By default copies the implementation from {@link java.util.Random#nextBytes(byte[])}.
     * @param array array to populate with random bytes
     */
    default void nextBytes(byte[] array) {
        // see Random#nextBytes
        for (int i = 0, len = array.length; i < len; ) {
            for (int rnd = nextInt(),
                 n = Math.min(len - i, Integer.SIZE / Byte.SIZE);
                 n-- > 0; rnd >>= Byte.SIZE) {
                array[i++] = (byte) rnd;
            }
        }
    }

    /**
     * Returns a random generator seed.
     * @throws UnsupportedOperationException as default implementation
     * @return generator seed
     */
    default long[] getSeed() {
        throw new UnsupportedOperationException();
    }
}
