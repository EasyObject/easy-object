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
import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.ast.UnaryExpression;

import static den.vor.easy.object.parser.TokenType.*;

public class UnaryParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        if (tokenHolder.match(MINUS)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operation.MINUS);
        }
        if (tokenHolder.match(NOT)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operation.NOT);
        }
        if (tokenHolder.match(PLUS)) {
            return new UnaryExpression(getNext().parse(tokenHolder), UnaryExpression.Operation.PLUS);
        }
        return getNext().parse(tokenHolder);
    }
}
