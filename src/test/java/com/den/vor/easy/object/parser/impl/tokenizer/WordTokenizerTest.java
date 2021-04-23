/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.impl.tokenizer;


import com.den.vor.easy.object.parser.InputHolder;
import com.den.vor.easy.object.parser.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class WordTokenizerTest extends TokenizerTestBase {

    private WordTokenizer wordTokenizer = new WordTokenizer();

    @ParameterizedTest
    @ValueSource(chars = {'a', '$'})
    void shouldSupportQuotes(char character) {
        boolean result = wordTokenizer.supports(character);

        assertTrue(result, () -> "Expected wordTokenizer to support " + character + " character");
    }

    @ParameterizedTest
    @ValueSource(chars = {'\"', ' ', '9', '+'})
    void shouldNotSupportCharsOtherThanQuotes(char character) {
        boolean result = wordTokenizer.supports(character);

        assertFalse(result, () -> "Expected wordTokenizer not to support " + character + " character");
    }

    @Test
    public void shouldReturnBooleanTrueToken_whenWordEqualsTrue() {
        InputHolder inputHolder = new InputHolder("true");

        assertToken(wordTokenizer.tokenize(inputHolder), TokenType.BOOLEAN, "true");
    }

    @Test
    public void shouldReturnBooleanFalseToken_whenWordEqualsFalse() {
        InputHolder inputHolder = new InputHolder("false");

        assertToken(wordTokenizer.tokenize(inputHolder), TokenType.BOOLEAN, "false");
    }

    @Test
    public void shouldReturnNullToken_whenWordEqualsNull() {
        InputHolder inputHolder = new InputHolder("null");

        assertToken(wordTokenizer.tokenize(inputHolder), TokenType.NONE, "");
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenWordEqualsDollarSign() {
        InputHolder inputHolder = new InputHolder("$");

        assertThrows(IllegalArgumentException.class, () -> wordTokenizer.tokenize(inputHolder));
    }

    @Test
    public void shouldReturnWordToken_whenWordIsNotSpecialCase() {
        InputHolder inputHolder = new InputHolder("hello");

        assertToken(wordTokenizer.tokenize(inputHolder), TokenType.WORD, "hello");
    }
}
