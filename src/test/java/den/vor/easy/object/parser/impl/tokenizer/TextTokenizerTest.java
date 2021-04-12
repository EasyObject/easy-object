/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.impl.tokenizer;


import den.vor.easy.object.parser.InputHolder;
import den.vor.easy.object.parser.TokenType;
import den.vor.easy.object.parser.exception.impl.UnexpectedEndOfInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TextTokenizerTest extends TokenizerTestBase {

    private TextTokenizer textTokenizer = new TextTokenizer();

    @ParameterizedTest
    @ValueSource(chars = {'\'', '\"'})
    void shouldSupportQuotes(char character) {
        boolean result = textTokenizer.supports(character);

        assertTrue(result, () ->"Expected textTokenizer to support " + character + " character");
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', ' ', '9', '+'})
    void shouldNotSupportCharsOtherThanQuotes(char character) {
        boolean result = textTokenizer.supports(character);

        assertFalse(result, () ->"Expected textTokenizer not to support " + character + " character");
    }

    @Test
    void shouldParseSingleQuotedTextIntoTextToken() {
        InputHolder inputHolder = new InputHolder("'hi'");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "hi");
    }

    @Test
    void shouldParseDoubleQuotedTextIntoTextToken() {
        InputHolder inputHolder = new InputHolder("\"hi\"");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "hi");
    }

    @Test
    void shouldParseSingleQuotedTextWithDoubleQuoteIntoTextToken() {
        InputHolder inputHolder = new InputHolder("'\"'");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "\"");
    }

    @Test
    void shouldParseDoubleQuotedTextWithSingleQuoteIntoTextToken() {
        InputHolder inputHolder = new InputHolder("\"'\"");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "'");
    }

    @Test
    void shouldParseDoubleQuotedTextWithEscapedDoubleQuoteIntoTextToken() {
        InputHolder inputHolder = new InputHolder("\"\\\"\"");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "\"");
    }

    @Test
    void shouldParseSingleQuotedTextWithEscapedSingleQuoteIntoTextToken() {
        InputHolder inputHolder = new InputHolder("'\\''");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "'");
    }

    @Test
    void shouldParseEscapedTabIntoTextToken() {
        InputHolder inputHolder = new InputHolder("'\\t'");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "\t");
    }

    @Test
    void shouldParseEscapedNewLineIntoTextToken() {
        InputHolder inputHolder = new InputHolder("'\\n'");

        assertToken(textTokenizer.tokenize(inputHolder), TokenType.TEXT, "\n");
    }

    @Test
    void shouldThrowException_whenQuotesAreNotClosed() {
        InputHolder inputHolder = new InputHolder("'");

        assertThrows(UnexpectedEndOfInputException.class, () -> textTokenizer.tokenize(inputHolder));
    }

}
