/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.random;


import java.util.List;

public interface CustomRandom {

    int nextInt();

    int nextInt(int bound);

    long nextLong();

    boolean nextBoolean();

    float nextFloat();

    double nextDouble();

    default double nextDouble(double min, double max) {
        return (max - min) * nextDouble() + min;
    }

    default long nextLong(long min, long max) {
        return min + (long) (nextDouble() * (max - min));
    }

    default long nextLongInclusive(long min, long max) {
        return nextLong(min, max + 1);
    }

    default int nextInt(int min, int max) {
        return nextInt(max - min) + min;
    }

    default int nextIntInclusive(int min, int max) {
        return nextInt(min, max + 1);
    }

    default boolean nextBoolean(double chances) {
        if (chances > 1 || chances < 0) {
            throw new IllegalArgumentException("Chances should be between 0 and 1");
        }
        return nextDouble() < chances;
    }

    default <T> T next(List<T> list) {
        return list.get(nextInt(list.size()));
    }

    default <T> T next(T[] array) {
        return array[nextInt(array.length)];
    }

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

    default long[] getSeed() {
        throw new UnsupportedOperationException();
    }
}
