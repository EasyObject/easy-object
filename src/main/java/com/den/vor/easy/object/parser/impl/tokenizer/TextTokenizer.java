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
import com.den.vor.easy.object.parser.exception.impl.UnexpectedEndOfInputException;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.TokenType;

/**
 * Tokenizer for strings.
 * Requires the next character to be single or double quote.
 */
public class TextTokenizer implements Tokenizer {

    /**
     * Supports the next character if it's a single or double quote.
     * See {@link Character#isDigit(char)}.
     * @param next next character
     * @return true if the character is digit, false otherwise
     */
    @Override
    public boolean supports(char next) {
        return next == '"' || next == '\'';
    }

    /**
     * Parses the next part of input. Returns {@link TokenType#TEXT} token type.
     * @throws UnexpectedEndOfInputException if the closing quote not found
     * @param inputHolder holder of the input string
     * @return next token
     */
    @Override
    public Token tokenize(InputHolder inputHolder) {
        char start = inputHolder.peek();
        inputHolder.next(); // skip quote
        StringBuilder buffer = new StringBuilder();
        char current = inputHolder.peek();
        while (true) {
            if (!inputHolder.hasMoreCharacters()) {
                throw new UnexpectedEndOfInputException('\'');
            }
            if (current == '\\') {
                current = inputHolder.next();
                switch (current) {
                    case '"':
                        current = inputHolder.next();
                        buffer.append('"');
                        continue;
                    case '\'':
                        current = inputHolder.next();
                        buffer.append('\'');
                        continue;
                    case 'n':
                        current = inputHolder.next();
                        buffer.append('\n');
                        continue;
                    case 't':
                        current = inputHolder.next();
                        buffer.append('\t');
                        continue;
                    default:
                        buffer.append('\\');
                        continue;
                }
            }
            if (current == start) {
                break;
            }
            buffer.append(current);
            current = inputHolder.next();
        }
        inputHolder.next(); // skip closing quote
        return new Token(TokenType.TEXT, buffer.toString());
    }
}
