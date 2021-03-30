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

import static den.vor.easy.object.parser.TokenType.LSHIFT;
import static den.vor.easy.object.parser.TokenType.RSHIFT;
import static den.vor.easy.object.parser.ast.BinaryExpression.Operator.LEFT_SHIFT;
import static den.vor.easy.object.parser.ast.BinaryExpression.Operator.RIGHT_SHIFT;

/**
 * Chain node that parses binary '>>' and '<<' operator tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link BinaryExpression} for details.
 */
public class ShiftParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = getNext().parse(tokenHolder);

        while (true) {
            if (tokenHolder.match(LSHIFT)) {
                result = new BinaryExpression(result, getNext().parse(tokenHolder), LEFT_SHIFT);
                continue;
            }
            if (tokenHolder.match(RSHIFT)) {
                result = new BinaryExpression(result, getNext().parse(tokenHolder), RIGHT_SHIFT);
                continue;
            }
            break;
        }

        return result;
    }
}
