/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.el;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArithmeticalOperationsTest {

    @Nested
    class SumTest {

        @Test
        void shouldReturnInt_whenSummingTwoIntsWithoutOverflow() {
            TestEvaluator.evaluate("5 + 10").assertEquals(15);
        }

        @Test
        void shouldReturnInt_whenSummingTwoIntsWithPositiveOverflow() {
            TestEvaluator.evaluate("1500000000 + 1500000001").assertEquals(-1294967295);
        }

        @Test
        void shouldReturnPositiveInt_whenSummingTwoNegativeIntsWithOverflow() {
            TestEvaluator.evaluate("-2000000000 + -1500000000").assertEquals(794967296);
        }

        @Test
        void shouldReturnDouble_whenSummingTwoDoubles() {
            TestEvaluator.evaluate("0.25 + 0.5").assertEquals(0.75);
        }

        @Test
        void shouldReturnDouble_whenSummingIntAndDouble() {
            TestEvaluator.evaluate("0.25 + 2").assertEquals(2.25);
        }

        @Test
        void shouldReturnString_whenAddingStringAndInt() {
            TestEvaluator.evaluate("'a' + 2").assertEquals("a2");
        }

        @Test
        void shouldReturnString_whenAddingDoubleAndString() {
            TestEvaluator.evaluate("10.0 + 'a'").assertEquals("10.0a");
        }
    }

    @Nested
    class MultiplicationTest {

        @Test
        void shouldReturnInt_whenMultiplyingInts() {
            TestEvaluator.evaluate("10 * 3").assertEquals(30);
        }

        @Test
        void shouldReturnDouble_whenMultiplyingIntAndDouble() {
            TestEvaluator.evaluate("10 * 3.").assertEquals(30.);
        }

        @Test
        void shouldReturnString_whenMultiplyingStringByInt() {
            TestEvaluator.evaluate("'ab' * 3").assertEquals("ababab");
        }
    }
}
