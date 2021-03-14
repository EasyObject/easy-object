/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.impl;

import den.vor.easy.object.parser.ParserChainNode;
import den.vor.easy.object.parser.TokenHolder;
import den.vor.easy.object.parser.ast.ConditionalExpression;
import den.vor.easy.object.parser.ast.Expression;

import static den.vor.easy.object.parser.TokenType.*;

public class ConditionalParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        while (true) {
            if (tokenHolder.match(LT)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder), ConditionalExpression.Operation.LT);
                continue;
            }
            if (tokenHolder.match(LTEQ)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder),
                        ConditionalExpression.Operation.LTEQ);
                continue;
            }
            if (tokenHolder.match(GT)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder), ConditionalExpression.Operation.GT);
                continue;
            }
            if (tokenHolder.match(GTEQ)) {
                result = new ConditionalExpression(result, parseNext(tokenHolder),
                        ConditionalExpression.Operation.GTEQ);
                continue;
            }
            break;
        }

        return result;
    }
}
