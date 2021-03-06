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

class LogicalOperationsTest {

    @Test
    void shouldReturnTrue_whenComparingTwoNumbers() {
        TestEvaluator.evaluate("2 > 1.5").assertEquals(true);
    }

    @Test
    void shouldReturnTrue_whenComparingTwoStrings() {
        TestEvaluator.evaluate("'a' <= 'b'").assertEquals(true);
    }

    @Nested
    class BooleanOperations {

        @Test
        void shouldReturnFalse_whenOneOfConditionsInAndIsFalse() {
            TestEvaluator.evaluate("1 > 0 && 2 < 1").assertEquals(false);
        }

        @Test
        void shouldReturnFalse_whenOneOfThreeConditionsInAndIsFalse() {
            TestEvaluator.evaluate("1 > 0 && 2 > 1 && 2 < 1").assertEquals(false);
        }

        @Test
        void shouldReturnTrue_whenOneOfConditionsInAndIsTrue() {
            TestEvaluator.evaluate("1 > 0 || 2 < 1").assertEquals(true);
        }
    }
}
