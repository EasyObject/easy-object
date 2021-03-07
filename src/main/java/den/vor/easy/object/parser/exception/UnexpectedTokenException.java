/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

import den.vor.easy.object.parser.Token;

public class UnexpectedTokenException extends ParserException {

    private final Token token;

    public UnexpectedTokenException(Token token) {
        this.token = token;
    }

    @Override
    public String getUserMessage() {
        return "Didn't expect token " + token.getType() + " (" + token.getText() + ")";
    }

    @Override
    public String toString() {
        return "UnexpectedTokenException{" +
                "token=" + token +
                '}';
    }
}
