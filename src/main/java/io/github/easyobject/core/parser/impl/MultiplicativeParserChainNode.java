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

import static io.github.easyobject.core.parser.TokenType.*;
import static io.github.easyobject.core.parser.ast.BinaryExpression.Operator.*;

/**
 * Chain node that parses binary '*', '/' and '%' operators tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link BinaryExpression} for details.
 */
public class MultiplicativeParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = getNext().parse(tokenHolder);

        while (true) {
            if (tokenHolder.match(STAR)) {
                result = new BinaryExpression(result, getNext().parse(tokenHolder), MULTIPLY);
                continue;
            }
            if (tokenHolder.match(SLASH)) {
                result = new BinaryExpression(result, getNext().parse(tokenHolder), DIVIDE);
                continue;
            }
            if (tokenHolder.match(PERCENT)) {
                result = new BinaryExpression(result, getNext().parse(tokenHolder), REMAINDER);
                continue;
            }
            break;
        }

        return result;
    }
}
