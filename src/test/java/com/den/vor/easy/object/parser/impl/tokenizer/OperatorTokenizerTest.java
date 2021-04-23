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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class OperatorTokenizerTest extends TokenizerTestBase {

    private OperatorTokenizer operatorTokenizer = new OperatorTokenizer();

    @ParameterizedTest
    @ValueSource(chars = {'^', '?', '+', '-', '*', '/', '(', ')', '[', ']', '{', '}', '=', '<', '>', '!', '&', '|',
            ',', ':', '.', '%'})
    void shouldSupportDigits(char character) {
        boolean result = operatorTokenizer.supports(character);

        assertTrue(result, () -> "Expected operatorTokenizer to support " + character + " character");
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', ' ', '"', '9'})
    void shouldNotSupportCharsOtherThanDigits(char character) {
        boolean result = operatorTokenizer.supports(character);

        assertFalse(result, () -> "Expected operatorTokenizer not to support " + character + " character");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'?\0', QUESTION_SIGN",
            "':\0', COLON",
            "'+\0', PLUS",
            "'-\0', MINUS",
            "'*\0', STAR",
            "'/\0', SLASH",
            "'%\0', PERCENT",
            "'**\0', POW",
            "'(\0', LPAREN",
            "')\0', RPAREN",
            "'[\0', LBRACKET",
            "']\0', RBRACKET",
            "'{\0', LBRACE",
            "'}\0', RBRACE",
            "'=\0', EQ",
            "'<\0', LT",
            "'>\0', GT",
            "',\0', COMMA",
            "'.\0', DOT",
            "'!\0', EXCL",
            "'&\0', AMP",
            "'|\0', BAR",
            "'^\0', CARET",
            "'==\0', EQEQ",
            "'!=\0', EXCLEQ",
            "'<=\0', LTEQ",
            "'>=\0', GTEQ",
            "'&&\0', AMPAMP",
            "'||\0', BARBAR",
            "'<<\0', LSHIFT",
            "'>>\0', RSHIFT"
    })
    void shouldTokenizeOperator(String text, TokenType type) {
        InputHolder inputHolder = new InputHolder(text);

        assertToken(operatorTokenizer.tokenize(inputHolder), type, "");
    }
}
