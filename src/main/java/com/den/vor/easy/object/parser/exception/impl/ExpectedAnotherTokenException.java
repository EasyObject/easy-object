/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.exception.impl;

import com.den.vor.easy.object.parser.ParserChainNode;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.TokenType;
import com.den.vor.easy.object.parser.exception.ExpressionLanguageException;

/**
 * Exception that is thrown when {@link ParserChainNode} expected one token, but got another.
 */
public class ExpectedAnotherTokenException extends ExpressionLanguageException {

    private final Token token;
    private final TokenType expected;

    public ExpectedAnotherTokenException(Token token, TokenType expected) {
        this.token = token;
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "ExpectedAnotherTokenException{" +
                "token=" + token +
                ", expected=" + expected +
                '}';
    }
}
