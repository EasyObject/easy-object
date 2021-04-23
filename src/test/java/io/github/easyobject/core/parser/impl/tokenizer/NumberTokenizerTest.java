/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.impl.tokenizer;

import io.github.easyobject.core.parser.exception.impl.InvalidPeriodFormatException;
import io.github.easyobject.core.parser.InputHolder;
import io.github.easyobject.core.parser.TokenType;
import io.github.easyobject.core.parser.exception.impl.FloatNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class NumberTokenizerTest extends TokenizerTestBase {

    private NumberTokenizer numberTokenizer = new NumberTokenizer();

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'})
    void shouldSupportDigits(char character) {
        boolean result = numberTokenizer.supports(character);

        assertTrue(result, () -> "Expected numberTokenizer to support " + character + " character");
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', ' ', '"', '+'})
    void shouldNotSupportCharsOtherThanDigits(char character) {
        boolean result = numberTokenizer.supports(character);

        assertFalse(result, () -> "Expected numberTokenizer not to support " + character + " character");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'0', INT_NUMBER, 0",
            "'10', INT_NUMBER, 10",
            "'03', INT_NUMBER, 03",
            "'22123123', INT_NUMBER, 22123123",
            "'1.0', DOUBLE_NUMBER, 1.0",
            "'1234.0123123', DOUBLE_NUMBER, 1234.0123123",
    })
    void shouldTokenizeNumbers(String text, TokenType type, String expectedText) {
        InputHolder inputHolder = new InputHolder(text);

        assertToken(numberTokenizer.tokenize(inputHolder), type, expectedText);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'1d', PERIOD, 1d",
            "'1d3y', PERIOD, 1d3y",
            "'1q2w3e4r5t6y7u8i9o', PERIOD, 1q2w3e4r5t6y7u8i9o",
    })
    void shouldTokenizePeriod(String text, TokenType type, String expectedText) {
        InputHolder inputHolder = new InputHolder(text);

        assertToken(numberTokenizer.tokenize(inputHolder), type, expectedText);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'1.2.3'",
            "'1..3'"
    })
    public void shouldThrowException_whenDoubleNumberHasTwoDots(String text) {
        InputHolder inputHolder = new InputHolder(text);

        assertThrows(FloatNumberException.class, () -> numberTokenizer.tokenize(inputHolder),
                () -> "Expected " + text + " to cause FloatNumberException");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'1.2y'",
            "'.1y'"
    })
    public void shouldThrowException_whenPeriodHasDot(String text) {
        InputHolder inputHolder = new InputHolder(text);

        assertThrows(InvalidPeriodFormatException.class, () -> numberTokenizer.tokenize(inputHolder),
                () -> "Expected " + text + " to cause InvalidPeriodFormatException");
    }
}
