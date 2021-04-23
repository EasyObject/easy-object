/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser;

/**
 * Class that holds string to be tokenized. See {@link Lexer} and {@link Tokenizer} for details.
 */
public class InputHolder {

    /**
     * String input to be parsed.
     */
    private String input;

    /**
     * Current character index.
     */
    private int pos;

    public InputHolder(String input) {
        this.input = input;
    }

    /**
     * Get the next character in the input. Changes internal counter, can not be undone.
     * See {@linkplain InputHolder#pos}.
     *
     * @return next character in the input string.
     */
    public char next() {
        pos++;
        return peek();
    }

    /**
     * Get the next character in the input without changing internal counter.
     *
     * @return next character in the input string.
     */
    public char peek() {
        if (pos >= input.length()) {
            return '\0';
        }
        return input.charAt(pos);
    }

    /**
     * @return whether input has more unread characters.
     */
    public boolean hasMoreCharacters() {
        return pos < input.length();
    }
}
