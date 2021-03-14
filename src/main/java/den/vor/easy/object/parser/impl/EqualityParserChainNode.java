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

import static den.vor.easy.object.parser.TokenType.EQEQ;
import static den.vor.easy.object.parser.TokenType.EXCLEQ;
import static den.vor.easy.object.parser.ast.ConditionalExpression.Operation.EQUALS;
import static den.vor.easy.object.parser.ast.ConditionalExpression.Operation.NOT_EQUALS;

public class EqualityParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        if (tokenHolder.match(EQEQ)) {
            return new ConditionalExpression(result, parseNext(tokenHolder), EQUALS);
        }
        if (tokenHolder.match(EXCLEQ)) {
            return new ConditionalExpression(result, parseNext(tokenHolder), NOT_EQUALS);
        }
        return result;
    }
}
