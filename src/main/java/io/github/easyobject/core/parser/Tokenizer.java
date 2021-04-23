/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser;

/**
 * Interface for objects that turn string input into {@link Token}s.
 * See {@link Lexer} for usage.
 */
public interface Tokenizer {

    /**
     * Determines if this tokenizer can turn the next input characters into {@link Token}s.
     * @param next next character
     * @return whether tokenizer supports next input characters
     */
    boolean supports(char next);

    /**
     * Turn the next input characters into {@link Token}.
     * Is expected to be called after {@link Tokenizer#supports(char)} returned {@code true}.
     * @param inputHolder holder of the input string
     * @return next token
     */
    Token tokenize(InputHolder inputHolder);
}
