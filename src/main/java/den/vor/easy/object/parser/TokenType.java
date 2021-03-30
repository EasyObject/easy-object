/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

/**
 * Enum that stores all possible token types.
 * See {@link Token} and {@link Lexer} for usage.
 */
public enum TokenType {
    QUESTION_SIGN,
    COLON,

    PERIOD,
    DATE,

    DOUBLE_NUMBER,
    INT_NUMBER,
    BOOLEAN,
    WORD,
    TEXT,
    IN,
    DOT,
    IS,
    NONE,
    PERCENT,
    POW,

    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQ,
    EQEQ,
    EXCL,
    EXCLEQ,
    LT,
    LTEQ,
    GT,
    GTEQ,
    NOT,

    BAR,
    BARBAR,
    AMP,
    AMPAMP,
    CARET,

    LPAREN, // (
    RPAREN, // )
    LBRACKET, // [
    RBRACKET, // ]
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    ARG, // ${

    LSHIFT, // <<
    RSHIFT, // >>

    EOF
}
