/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

/**
 * Exception that is thrown when {@link den.vor.easy.object.parser.Lexer} unexpectedly reaches the end on expression.
 * Example: {@code "a} (quotes do not close).
 */
public class UnexpectedEndOfInput extends ELException {

    private final char expectedChar;

    public UnexpectedEndOfInput(char expectedChar) {
        this.expectedChar = expectedChar;
    }
}
