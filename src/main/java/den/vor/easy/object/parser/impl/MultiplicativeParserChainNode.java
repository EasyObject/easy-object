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
import den.vor.easy.object.parser.ast.BinaryExpression;
import den.vor.easy.object.parser.ast.Expression;

import static den.vor.easy.object.parser.TokenType.*;
import static den.vor.easy.object.parser.ast.BinaryExpression.Operation.*;

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
