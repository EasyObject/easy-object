/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.exception.impl;

import io.github.easyobject.core.parser.Lexer;
import io.github.easyobject.core.parser.exception.ExpressionLanguageException;

/**
 * Exception that is thrown when {@link Lexer} unexpectedly reaches the end on expression.
 * Example: {@code "a} (quotes do not close).
 */
public class UnexpectedEndOfInputException extends ExpressionLanguageException {

    private final char expectedChar;

    public UnexpectedEndOfInputException(char expectedChar) {
        this.expectedChar = expectedChar;
    }

    @Override
    public String toString() {
        return "UnexpectedEndOfInputException{" +
                "expectedChar=" + expectedChar +
                '}';
    }
}
