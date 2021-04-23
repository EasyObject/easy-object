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
import com.den.vor.easy.object.parser.ast.BinaryExpression;
import com.den.vor.easy.object.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BitwiseOrParserChainNodeTest extends ParserChainNodeTestBase {

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

    private BitwiseOrParserChainNode bitwiseOrParserChainNode;

    @BeforeEach
    void setUp() {
        bitwiseOrParserChainNode = new BitwiseOrParserChainNode();
        bitwiseOrParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.BAR)).thenReturn(false);

        Expression expression = bitwiseOrParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + bitwiseOrParserChainNode + " to return next node's" +
                " result when doesn't match '|', got " + expression);
    }

    @Test
    void shouldReturnBinaryExpressionWithAndOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.BAR)).thenReturn(true).thenReturn(false);

        Expression expression = bitwiseOrParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.OR);

        verify(tokenHolder, times(2)).match(TokenType.BAR);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnNestedBinaryExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.BAR)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = bitwiseOrParserChainNode.parse(tokenHolder);

        BinaryExpression binaryExpression = assertBinaryExpression(expression);

        Expression left = binaryExpression.getLeft();

        BinaryExpression leftBinaryExpression =
                assertBinaryExpressionWithChildren(left, firstResult, secondResult, BinaryExpression.Operator.OR);

        assertBinaryExpressionChildren(binaryExpression, leftBinaryExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.BAR);
        verify(next, times(3)).parse(tokenHolder);
    }
}
