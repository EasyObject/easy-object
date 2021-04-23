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
 * Represents an expression language token.
 * See {@link Lexer} and {@link Parser} for usages.
 */
public class Token {

    /**
     * Token type.
     */
    private final TokenType type;
    /**
     * Text that {@link Lexer} parsed into this token.
     */
    private final String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public Token(TokenType type) {
        this(type, "");
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return type + " " + text;
    }
}
