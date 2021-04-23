/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.impl;

import com.den.vor.easy.object.parser.ParserChainNode;
import com.den.vor.easy.object.parser.TokenHolder;
import com.den.vor.easy.object.parser.ast.BinaryExpression;
import com.den.vor.easy.object.parser.ast.Expression;

import static com.den.vor.easy.object.parser.TokenType.CARET;

/**
 * Chain node that parses bitwise '^' operator tokens.
 * More high-precedence tokens must be parsed in the later chain nodes.
 * See {@link BinaryExpression} for details.
 */
public class BitwiseXorParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        Expression result = parseNext(tokenHolder);

        while (true) {
            if (tokenHolder.match(CARET)) {
                result = new BinaryExpression(result, parseNext(tokenHolder), BinaryExpression.Operator.XOR);
                continue;
            }
            break;
        }

        return result;
    }
}
