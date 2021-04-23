/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.exception.impl;

import com.den.vor.easy.object.parser.ParserChain;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.exception.ExpressionLanguageException;

/**
 * Exception that is thrown when {@link ParserChain} doesn't know how to parse a token.
 */
public class UnexpectedTokenException extends ExpressionLanguageException {

    private final Token token;

    public UnexpectedTokenException(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UnexpectedTokenException{" +
                "token=" + token +
                '}';
    }
}
