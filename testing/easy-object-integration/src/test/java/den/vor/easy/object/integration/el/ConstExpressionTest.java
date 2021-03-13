/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.el;

import org.junit.jupiter.api.Test;

import static den.vor.easy.object.integration.el.TestEvaluator.evaluate;

class ConstExpressionTest {

    @Test
    void shouldReturnInt_whenLiteralContainsOnlyDigits() {
        evaluate("2").assertEquals(2);
    }

    @Test
    void shouldReturnDouble_whenLiteralContainsDigitsAndOneDot() {
        evaluate("2.3").assertEquals(2.3);
    }

    @Test
    void shouldReturnString_whenLiteralStartsAndEndsWithQuote() {
        evaluate("'someText'").assertEquals("someText");
    }

    @Test
    void shouldReturnBooleanTrue_whenLiteralIsTrue() {
        evaluate("true").assertEquals(true);
    }

    @Test
    void shouldReturnBooleanFalse_whenLiteralIsFalse() {
        evaluate("false").assertEquals(false);
    }
}
