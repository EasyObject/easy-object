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

public class TokenHolder {

    private static final Token EOF_TOKEN = new Token(EOF, "");

    private List<Token> tokens;
    private int size;
    private int pos;

    public TokenHolder(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            throw new ExpectedAnotherTokenException(current, type);
        }
        pos++;
        return current;
    }

    public boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    public boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    public boolean lookMatch(TokenType type) {
        return lookMatch(0, type);
    }

    public Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) {
            return EOF_TOKEN;
        }
        return tokens.get(position);
    }
}
