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
import io.github.easyobject.core.parser.ast.ConditionalExpression;
import io.github.easyobject.core.parser.ast.Expression;

import static io.github.easyobject.core.parser.TokenType.*;

/**
 * Chain node that parses logical binary comparison operators, such as {@code >}, {@code <}, {@code >=}, {@code <=}.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link ConditionalExpression} for details.
 */
public class ConditionalParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        while (true) {
            if (tokenHolder.match(LT)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder), ConditionalExpression.Operator.LT);
                continue;
            }
            if (tokenHolder.match(LTEQ)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder),
                        ConditionalExpression.Operator.LTEQ);
                continue;
            }
            if (tokenHolder.match(GT)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder), ConditionalExpression.Operator.GT);
                continue;
            }
            if (tokenHolder.match(GTEQ)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder),
                        ConditionalExpression.Operator.GTEQ);
                continue;
            }
            break;
        }

        return result;
    }
}
