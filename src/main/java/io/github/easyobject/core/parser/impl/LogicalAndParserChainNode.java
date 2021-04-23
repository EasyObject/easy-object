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

import static io.github.easyobject.core.parser.TokenType.AMPAMP;

/**
 * Chain node that parses logical binary {@code &&} operator tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link ConditionalExpression} for details.
 */
public class LogicalAndParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        while (true) {
            if (tokenHolder.match(AMPAMP)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder), ConditionalExpression.Operator.AND);
                continue;
            }
            break;
        }

        return result;
    }
}
