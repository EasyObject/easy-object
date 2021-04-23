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
import io.github.easyobject.core.parser.ast.BinaryExpression;
import io.github.easyobject.core.parser.ast.Expression;

import static io.github.easyobject.core.parser.TokenType.POW;

/**
 * Chain node that parses binary '**' operator tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link BinaryExpression} for details.
 */
public class PowerParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = getNext().parse(tokenHolder);

        while (tokenHolder.match(POW)) {
            result = new BinaryExpression(result, getNext().parse(tokenHolder), BinaryExpression.Operator.POW);
        }

        return result;
    }
}
