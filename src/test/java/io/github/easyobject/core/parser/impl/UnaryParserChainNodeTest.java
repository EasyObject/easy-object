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
import io.github.easyobject.core.parser.TokenType;
import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.ast.UnaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnaryParserChainNodeTest extends ParserChainNodeTestBase {

    @Mock
    private ParserChainNode next;
    @Mock
    private ParserChainNode root;
    @Mock
    private Expression firstResult;
    @Mock
    private TokenHolder tokenHolder;

    private UnaryParserChainNode unaryParserChainNode;

    @BeforeEach
    void setUp() {
        unaryParserChainNode = new UnaryParserChainNode();
        unaryParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.MINUS)).thenReturn(false);
        when(tokenHolder.match(TokenType.NOT)).thenReturn(false);
        when(tokenHolder.match(TokenType.PLUS)).thenReturn(false);

        Expression expression = unaryParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + unaryParserChainNode + " to return next " +
                "node's result when doesn't match '+', '-' or '!', got " + expression);
    }

    @Test
    void shouldReturnUnaryExpressionWithPlusOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.MINUS)).thenReturn(false);
        when(tokenHolder.match(TokenType.NOT)).thenReturn(false);
        when(tokenHolder.match(TokenType.PLUS)).thenReturn(true);

        Expression expression = unaryParserChainNode.parse(tokenHolder);

        assertUnaryExpressionWithChild(expression, firstResult, UnaryExpression.Operator.PLUS);

        verify(tokenHolder).match(TokenType.PLUS);
        verify(next, times(1)).parse(tokenHolder);
    }

    @Test
    void shouldReturnUnaryExpressionWithMinusOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.MINUS)).thenReturn(true);

        Expression expression = unaryParserChainNode.parse(tokenHolder);

        assertUnaryExpressionWithChild(expression, firstResult, UnaryExpression.Operator.MINUS);

        verify(tokenHolder).match(TokenType.MINUS);
        verify(next, times(1)).parse(tokenHolder);
    }

    @Test
    void shouldReturnUnaryExpressionWithNotOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.MINUS)).thenReturn(false);
        when(tokenHolder.match(TokenType.NOT)).thenReturn(true);

        Expression expression = unaryParserChainNode.parse(tokenHolder);

        assertUnaryExpressionWithChild(expression, firstResult, UnaryExpression.Operator.NOT);

        verify(tokenHolder).match(TokenType.NOT);
        verify(next, times(1)).parse(tokenHolder);
    }

}
