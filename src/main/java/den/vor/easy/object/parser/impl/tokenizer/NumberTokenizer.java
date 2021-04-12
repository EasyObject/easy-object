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
import den.vor.easy.object.parser.Token;
import den.vor.easy.object.parser.TokenType;
import den.vor.easy.object.parser.Tokenizer;
import den.vor.easy.object.parser.exception.impl.FloatNumberException;
import den.vor.easy.object.parser.exception.impl.InvalidPeriodFormatException;

/**
 * Tokenizer for number and period tokens.
 * Requires the next character to be digit.
 */
public class NumberTokenizer implements Tokenizer {

    /**
     * Supports the next character if it's a digit.
     * See {@link Character#isDigit(char)}.
     * @param next next character
     * @return true if the character is digit, false otherwise
     */
    @Override
    public boolean supports(char next) {
        return Character.isDigit(next);
    }

    /**
     * Parses the next part of input. May return
     * * {@link TokenType#INT_NUMBER} if finds only digits.
     * * {@link TokenType#DOUBLE_NUMBER} if finds digits with a single dot.
     * * {@link TokenType#PERIOD} if finds digits and then letters.
     * @throws FloatNumberException if more than one dot found in number
     * @throws InvalidPeriodFormatException if period contains the dot
     * @param inputHolder holder of the input string
     * @return next token
     */
    @Override
    public Token tokenize(InputHolder inputHolder) {
        final StringBuilder buffer = new StringBuilder();
        char current = inputHolder.peek();
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1) {
                    throw new FloatNumberException();
                }
            } else if (!Character.isDigit(current)) {
                if (Character.isLetter(current)) {
                    return tokenizePeriod(buffer.append(current), inputHolder);
                }
                break;
            }
            buffer.append(current);
            current = inputHolder.next();
        }
        if (buffer.indexOf(".") != -1) {
            return new Token(TokenType.DOUBLE_NUMBER, buffer.toString());
        } else {
            return new Token(TokenType.INT_NUMBER, buffer.toString());
        }
    }

    private Token tokenizePeriod(StringBuilder buffer, InputHolder inputHolder) {
        if (buffer.indexOf(".") != -1) {
            throw new InvalidPeriodFormatException(buffer.toString());
        }

        char current = inputHolder.next();
        while (Character.isDigit(current) || Character.isLetter(current)) {
            buffer.append(current);
            current = inputHolder.next();
        }
        return new Token(TokenType.PERIOD, buffer.toString());
    }
}
