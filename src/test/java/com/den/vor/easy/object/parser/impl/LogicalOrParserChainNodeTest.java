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
import com.den.vor.easy.object.parser.TokenType;
import com.den.vor.easy.object.parser.ast.ConditionalExpression;
import com.den.vor.easy.object.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogicalOrParserChainNodeTest extends ParserChainNodeTestBase {

    @Mock
    private ParserChainNode next;
    @Mock
    private ParserChainNode root;
    @Mock
    private Expression firstResult;
    @Mock
    private Expression secondResult;
    @Mock
    private Expression thirdResult;
    @Mock
    private TokenHolder tokenHolder;

    private LogicalOrParserChainNode logicalOrParserChainNode;

    @BeforeEach
    void setUp() {
        logicalOrParserChainNode = new LogicalOrParserChainNode();
        logicalOrParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.BARBAR)).thenReturn(false);

        Expression expression = logicalOrParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + logicalOrParserChainNode + " to return next " +
                "node's result when doesn't match '||', got " + expression);
    }

    @Test
    void shouldReturnConditionalExpressionWithOrOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.BARBAR)).thenReturn(true).thenReturn(false);

        Expression expression = logicalOrParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operator.OR);

        verify(tokenHolder, times(2)).match(TokenType.BARBAR);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnNestedConditionalExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.BARBAR)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = logicalOrParserChainNode.parse(tokenHolder);

        ConditionalExpression conditionalExpression = assertConditionalExpression(expression);

        Expression left = conditionalExpression.getLeft();

        ConditionalExpression leftBinaryExpression = assertConditionalExpressionWithChildren(left, firstResult,
                secondResult, ConditionalExpression.Operator.OR);

        assertConditionalExpressionChildren(conditionalExpression, leftBinaryExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.BARBAR);
        verify(next, times(3)).parse(tokenHolder);
    }
}
