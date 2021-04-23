/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.impl;

import com.den.vor.easy.object.parser.ParserChainNode;
import com.den.vor.easy.object.parser.TokenHolder;
import com.den.vor.easy.object.parser.ast.Expression;
import com.den.vor.easy.object.parser.ast.TernaryExpression;

import static com.den.vor.easy.object.parser.TokenType.COLON;
import static com.den.vor.easy.object.parser.TokenType.QUESTION_SIGN;

/**
 * Chain node that ternary expression tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link TernaryExpression} for details.
 */
public class TernaryParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        if (tokenHolder.match(QUESTION_SIGN)) {
            Expression then = getRoot().parse(tokenHolder);
            tokenHolder.consume(COLON);
            Expression elseExpr = getRoot().parse(tokenHolder);

            return new TernaryExpression(result, then, elseExpr);
        }

        return result;
    }
}
