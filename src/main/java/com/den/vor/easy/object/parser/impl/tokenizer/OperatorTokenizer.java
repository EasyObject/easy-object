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

import java.util.Map;

import static java.util.Map.entry;

/**
 * Tokenizer for operator tokens.
 * Requires the next character to be one of {@code ^?+-*()[]{}=<>!&|,:.%\/}.
 */
public class OperatorTokenizer implements Tokenizer {

    /**
     * Characters that may be found in operators.
     */
    private static final String OPERATOR_CHARS = "^?+-*/()[]{}=<>!&|,:.%";

    /**
     * Map of operators. Keys are strings that represent an operator, values are corresponding {@link TokenType} values.
     */
    private static final Map<String, TokenType> OPERATORS;

    static {

        OPERATORS = Map.ofEntries(
                entry("?", TokenType.QUESTION_SIGN),
                entry(":", TokenType.COLON),
                entry("+", TokenType.PLUS),
                entry("-", TokenType.MINUS),
                entry("*", TokenType.STAR),
                entry("/", TokenType.SLASH),
                entry("%", TokenType.PERCENT),
                entry("**", TokenType.POW),
                entry("(", TokenType.LPAREN),
                entry(")", TokenType.RPAREN),
                entry("[", TokenType.LBRACKET),
                entry("]", TokenType.RBRACKET),
                entry("{", TokenType.LBRACE),
                entry("}", TokenType.RBRACE),
                entry("=", TokenType.EQ),
                entry("<", TokenType.LT),
                entry(">", TokenType.GT),
                entry(",", TokenType.COMMA),
                entry(".", TokenType.DOT),
                entry("!", TokenType.EXCL),
                entry("&", TokenType.AMP),
                entry("|", TokenType.BAR),
                entry("^", TokenType.CARET),
                entry("==", TokenType.EQEQ),
                entry("!=", TokenType.EXCLEQ),
                entry("<=", TokenType.LTEQ),
                entry(">=", TokenType.GTEQ),
                entry("&&", TokenType.AMPAMP),
                entry("||", TokenType.BARBAR),
                entry("<<", TokenType.LSHIFT),
                entry(">>", TokenType.RSHIFT));
    }

    /**
     * Supports the next character if it's one of {@code ^?+-*()[]{}=<>!&|,:.%\/}.
     * See {@link OperatorTokenizer#OPERATOR_CHARS}.
     * @param next next character
     * @return true if the character is supportable, false otherwise
     */
    @Override
    public boolean supports(char next) {
        return OPERATOR_CHARS.indexOf(next) != -1;
    }

    /**
     * Parses the next part of input.
     * For possible token types see {@linkplain OperatorTokenizer#OPERATORS} map.
     * @param inputHolder holder of the input string
     * @return next token
     */
    @Override
    public Token tokenize(InputHolder inputHolder) {
        char current = inputHolder.peek();
        StringBuilder buffer = new StringBuilder();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                return new Token(OPERATORS.get(text));
            }
            buffer.append(current);
            current = inputHolder.next();
        }
    }
}
