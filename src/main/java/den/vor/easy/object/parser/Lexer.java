/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

import den.vor.easy.object.parser.exception.FloatNumberException;
import den.vor.easy.object.parser.exception.InvalidNameException;
import den.vor.easy.object.parser.exception.UnexpectedEndOfInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public final class Lexer {

    private static final String OPERATOR_CHARS = "^?+-*/()[]{}=<>!&|,:.%";

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

    private final String input;
    private final int length;
    private final List<Token> tokens;
    private int pos;

    public Lexer(String input) {
        this.input = input;
        length = input.length();

        tokens = new ArrayList<>();
    }

    public TokenHolder tokenize() {
        while (pos < length) {
            final char current = peek(0);
            if (Character.isDigit(current)) {
                tokenizeNumber();
            } else if (Character.isLetter(current) || current == '$') {
                tokenizeWord();
            } else if (current == '"' || current == '\'') {
                tokenizeText();
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else if (current == '\n') {
                next();
            } else {
                // whitespaces
                next();
            }
        }
        return new TokenHolder(tokens);
    }

    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1) {
                    throw new FloatNumberException();
                }
            } else if (!Character.isDigit(current)) {
                if (Character.isLetter(current)) {
                    if (buffer.indexOf(".") != -1) {
                        throw new InvalidNameException(buffer.append(current).toString());
                    } else {
                        tokenizePeriod(buffer);
                        return;
                    }
                }
                break;
            }
            buffer.append(current);
            current = next();
        }
        if (buffer.indexOf(".") != -1) {
            addToken(TokenType.DOUBLE_NUMBER, buffer.toString());
        } else {
            addToken(TokenType.INT_NUMBER, buffer.toString());
        }
    }

    private void tokenizePeriod(StringBuilder buffer) {
        char current = peek(0);
        while (Character.isDigit(current) || Character.isLetter(current)) {
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.PERIOD, buffer.toString());
    }

    private void tokenizeOperator() {
        char current = peek(0);
        StringBuilder buffer = new StringBuilder();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                addToken(OPERATORS.get(text));
                return;
            }
            buffer.append(current);
            current = next();
        }
    }

    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (isWordChar(current)) {
            buffer.append(current);
            current = next();
        }

        final String word = buffer.toString();
        switch (word) {
            case "true":
            case "false":
                addToken(TokenType.BOOLEAN, word);
                break;
            case "and":
                addToken(TokenType.AMPAMP);
                break;
            case "or":
                addToken(TokenType.BARBAR);
                break;
            case "not":
                addToken(TokenType.NOT);
                break;
            case "in":
                addToken(TokenType.IN);
                break;
            case "is":
                addToken(TokenType.IS);
                break;
            case "None":
                addToken(TokenType.NONE);
                break;
            default:
                if (word.equals("$")) {
                    throw new RuntimeException();
                }
                addToken(TokenType.WORD, word);
                break;
        }
    }

    private boolean isWordChar(char current) {
        return Character.isLetterOrDigit(current) || current == '_' || current == '$';
    }

    private void tokenizeText() {
        char start = peek(0);
        next(); // skip quote
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (pos >= length) {
                throw new UnexpectedEndOfInput('\'');
            }
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"':
                        current = next();
                        buffer.append('"');
                        continue;
                    case '\'':
                        current = next();
                        buffer.append('\'');
                        continue;
                    case 'n':
                        current = next();
                        buffer.append('\n');
                        continue;
                    case 't':
                        current = next();
                        buffer.append('\t');
                        continue;
                }
                buffer.append('\\');
                continue;
            }
            if (current == start) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        next(); // skip closing quote
        addToken(TokenType.TEXT, buffer.toString());
    }

    private char next() {
        pos++;
        return peek(0);
    }

    private char peek(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= length) {
            return '\0';
        }
        return input.charAt(position);
    }

    private void addToken(TokenType type) {
        addToken(type, "");
    }

    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }

}
