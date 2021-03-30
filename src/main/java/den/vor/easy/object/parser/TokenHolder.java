/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

import den.vor.easy.object.parser.exception.ExpectedAnotherTokenException;

import java.util.List;

import static den.vor.easy.object.parser.TokenType.EOF;

/**
 * Class that provides access to tokens during parsing process.
 * Holds a collection of {@link Token} and pointer to the next token to be parsed.
 */
public class TokenHolder {

    private static final Token EOF_TOKEN = new Token(EOF, "");

    private List<Token> tokens;
    private int size;
    private int pos;

    /**
     * Creates a new holder with provided tokens
     */
    public TokenHolder(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    /**
     * If the current token has a provided type, consumes it by moving the pointer to the next token.
     * @throws ExpectedAnotherTokenException if token type doesn't match
     * @param type required token type
     * @return consumed token
     */
    public Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            throw new ExpectedAnotherTokenException(current, type);
        }
        pos++;
        return current;
    }

    /**
     * If the current token has a provided type, consumes it by moving the pointer to the next token.
     * @param type required token type
     * @return true if token type matched, false otherwise
     */
    public boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    /**
     * Looks if a token on a specified offset matches type.
     * @param pos offset from the current token
     * @param type required token type
     * @return true if token type matches, false otherwise
     */
    public boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    /**
     * Looks if a current token matches provided type.
     * @param type required token type
     * @return true if token type matches, false otherwise
     */
    public boolean lookMatch(TokenType type) {
        return lookMatch(0, type);
    }

    /**
     * Gets the token on a specified offset from the current.
     * @param relativePosition offset from the current token
     * @return token on offset or {@link TokenHolder#EOF_TOKEN} if offset is out of bounds.
     */
    public Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) {
            return EOF_TOKEN;
        }
        return tokens.get(position);
    }
}
