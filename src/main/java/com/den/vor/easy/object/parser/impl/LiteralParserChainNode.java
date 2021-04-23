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
import com.den.vor.easy.object.parser.ast.*;
import com.den.vor.easy.object.parser.exception.impl.UnexpectedTokenException;
import com.den.vor.easy.object.value.impl.*;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.util.PeriodParser;

import java.util.ArrayList;
import java.util.List;

import static com.den.vor.easy.object.parser.TokenType.*;

/**
 * Chain node that parses literals, map and array access, method and function calls.
 * By default is the last node in the chain.
 */
public class LiteralParserChainNode extends ParserChainNode {

    @Override
    public Expression parse(TokenHolder tokenHolder) {
        final Token current = tokenHolder.get(0);

        Expression expression;
        if (tokenHolder.match(INT_NUMBER)) {
            IntValue intValue = IntValue.of(Integer.parseInt(current.getText()));
            expression = new ValueExpression(intValue);
        } else if (tokenHolder.match(DOUBLE_NUMBER)) {
            DoubleValue doubleValue = DoubleValue.of(Double.parseDouble(current.getText()));
            expression = new ValueExpression(doubleValue);
        } else if (tokenHolder.match(PERIOD)) {
            PeriodValue periodValue = PeriodValue.of(PeriodParser.parse(current.getText()));
            expression = new ValueExpression(periodValue);
        } else if (tokenHolder.match(BOOLEAN)) {
            BooleanValue booleanValue = BooleanValue.of(Boolean.parseBoolean(current.getText()));
            expression = new ValueExpression(booleanValue);
        } else if (tokenHolder.match(TEXT)) {
            StringValue stringValue = StringValue.of(current.getText());
            expression = new ValueExpression(stringValue);
        } else if (tokenHolder.lookMatch(WORD)) {
            if (tokenHolder.lookMatch(1, LPAREN)) {
                expression = functionCall(tokenHolder);
            } else {
                expression = getVarAccessChain(tokenHolder);
            }
        } else if (tokenHolder.match(LPAREN)) {
            Expression result = getRoot().parse(tokenHolder);
            tokenHolder.match(RPAREN);
            return result;
        } else {
            throw new UnexpectedTokenException(tokenHolder.get(0));
        }

        return rec(tokenHolder, expression);
    }

    private Expression functionCall(TokenHolder tokenHolder) {
        String methodName = tokenHolder.consume(WORD).getText();
        tokenHolder.consume(LPAREN);
        List<Expression> arguments = new ArrayList<>();
        while (!tokenHolder.lookMatch(RPAREN)) {
            if (!arguments.isEmpty()) {
                tokenHolder.consume(COMMA);
            }
            arguments.add(getRoot().parse(tokenHolder));
        }
        tokenHolder.consume(RPAREN);
        List<Expression> keys = List.of(new ValueExpression(StringValue.of(methodName)));
        Expression functionName = new VariableMapAccessExpression(keys);
        return rec(tokenHolder, new FunctionInvocationExpression(functionName, arguments));
    }

    private Expression getVarAccessChain(TokenHolder tokenHolder) {
        List<Expression> keys = new ArrayList<>();
        String text = tokenHolder.consume(WORD).getText();
        keys.add(new ValueExpression(StringValue.of(text)));
        while (true) {
            if (tokenHolder.lookMatch(DOT) && tokenHolder.lookMatch(1, WORD) &&
                    !tokenHolder.lookMatch(2, LPAREN)) {
                tokenHolder.consume(DOT);
                String key = tokenHolder.consume(WORD).getText();
                keys.add(new ValueExpression(StringValue.of(key)));
            } else if (tokenHolder.lookMatch(LBRACKET)) {
                tokenHolder.consume(LBRACKET);
                keys.add(getRoot().parse(tokenHolder));
                tokenHolder.consume(RBRACKET);
            } else {
                break;
            }
        }
        return new VariableMapAccessExpression(keys);
    }

    private Expression rec(TokenHolder tokenHolder, Expression expression) {
        if (tokenHolder.lookMatch(DOT) && tokenHolder.lookMatch(1, WORD)) {
            if (tokenHolder.lookMatch(2, LPAREN)) {
                return getMethodInvocationExpression(tokenHolder, expression);
            } else {
                tokenHolder.consume(DOT);
                String key = tokenHolder.consume(WORD).getText();
                ValueExpression e1 = new ValueExpression(StringValue.of(key));
                return rec(tokenHolder, new MapAccessExpression(expression, List.of(e1)));
            }
        } else if (tokenHolder.lookMatch(LBRACKET)) {
            tokenHolder.consume(LBRACKET);
            MapAccessExpression mapAccessExpression =
                    new MapAccessExpression(expression, List.of(getRoot().parse(tokenHolder)));
            tokenHolder.consume(RBRACKET);
            return rec(tokenHolder, mapAccessExpression);
        }
        return expression;
    }

    private Expression getMethodInvocationExpression(TokenHolder tokenHolder, Expression expression) {
        tokenHolder.consume(DOT);
        String methodName = tokenHolder.consume(WORD).getText();
        tokenHolder.consume(LPAREN);
        List<Expression> arguments = new ArrayList<>();
        while (!tokenHolder.lookMatch(RPAREN)) {
            if (!arguments.isEmpty()) {
                tokenHolder.consume(COMMA);
            }
            arguments.add(getRoot().parse(tokenHolder));
        }
        ValueExpression method = new ValueExpression(StringValue.of(methodName));
        return rec(tokenHolder, new MethodInvocationExpression(expression, method, arguments));
    }

}
