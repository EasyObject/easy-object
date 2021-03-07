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
import den.vor.easy.object.parser.ast.TernaryExpression;

import static den.vor.easy.object.parser.TokenType.*;

public class TernaryParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        if (tokenHolder.match(QUESTION_SIGN)) {
            Expression then = getRoot().parse(tokenHolder);
            tokenHolder.consume(COLON);
            Expression elseExpr = getRoot().parse(tokenHolder);

            return new TernaryExpression(result, then, elseExpr);
        }

        return result;
    }
}
