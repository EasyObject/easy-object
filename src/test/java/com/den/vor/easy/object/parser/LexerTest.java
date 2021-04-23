/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser;

import org.junit.jupiter.api.Test;

import static com.den.vor.easy.object.parser.TokenType.EOF;
import static com.den.vor.easy.object.parser.TokenType.INT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {

    @Test
    void shouldReturnEmptyTokenHolder_whenNoTokenizerSupportsSymbol() {
        TokenHolder holder = new Lexer(" ").tokenize();

        Token token = holder.get(0);
        assertEquals(token.getType(), EOF, () -> "Expected lexer to return an empty token holder, got " + holder);
    }

    @Test
    void shouldReturnNonEmptyTokenHolder_whenTokenizerSupportsSymbol() {
        TokenHolder holder = new Lexer("1").tokenize();

        Token token = holder.get(0);
        assertEquals(token.getType(), INT_NUMBER,
                () -> "Expected lexer to return an empty token holder, got " + holder);
    }
}
