/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.impl;

import io.github.easyobject.core.parser.ParserChainNode;
import io.github.easyobject.core.parser.TokenHolder;
import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.ast.TernaryExpression;

import static io.github.easyobject.core.parser.TokenType.COLON;
import static io.github.easyobject.core.parser.TokenType.QUESTION_SIGN;

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
