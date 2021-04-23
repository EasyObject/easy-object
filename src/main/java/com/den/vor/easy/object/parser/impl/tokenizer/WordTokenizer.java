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
import com.den.vor.easy.object.parser.Tokenizer;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.TokenType;

/**
 * Tokenizer for words tokens (literals and variable references).
 * Requires the next character to be a letter or the dollar sign.
 */
public class WordTokenizer implements Tokenizer {

    /**
     * Supports the next character if it's a letter or a dollar sign.
     * See {@link Character#isLetter(char)}.
     * @param next next character
     * @return true if the character is supported, false otherwise
     */
    @Override
    public boolean supports(char next) {
        return Character.isLetter(next) || next == '$';
    }

    /**
     * Parses the next part of input. May return
     * * {@link TokenType#BOOLEAN} if word is {@code true} or {@code false}.
     * * {@link TokenType#NONE} if word is {@code null}.
     * * {@link TokenType#WORD} otherwise.
     * @throws IllegalArgumentException if word equals "$"
     * @param inputHolder holder of the input string
     * @return next token
     */
    @Override
    public Token tokenize(InputHolder inputHolder) {
        final StringBuilder buffer = new StringBuilder();
        char current = inputHolder.peek();
        while (isWordChar(current)) {
            buffer.append(current);
            current = inputHolder.next();
        }

        final String word = buffer.toString();
        switch (word) {
            case "true":
            case "false":
                return new Token(TokenType.BOOLEAN, word);
            case "null":
                return new Token(TokenType.NONE);
            default:
                if (word.equals("$")) {
                    throw new IllegalArgumentException("Word can not consist of $ sign only");
                }
                return new Token(TokenType.WORD, word);
        }
    }

    private boolean isWordChar(char current) {
        return Character.isLetterOrDigit(current) || current == '_' || current == '$';
    }
}
