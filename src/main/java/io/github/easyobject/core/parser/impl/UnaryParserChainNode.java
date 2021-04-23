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
import io.github.easyobject.core.parser.ast.UnaryExpression;

import static io.github.easyobject.core.parser.TokenType.*;

/**
 * Chain node that unary '+', '-', '!' operators.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link UnaryExpression} for details.
 */
public class UnaryParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        if (tokenHolder.match(MINUS)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operator.MINUS);
        }
        if (tokenHolder.match(NOT)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operator.NOT);
        }
        if (tokenHolder.match(PLUS)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operator.PLUS);
        }
        return getNext().parse(tokenHolder);
    }
}
