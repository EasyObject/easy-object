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
import io.github.easyobject.core.parser.ast.BinaryExpression;
import io.github.easyobject.core.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PowerParserChainNodeTest extends ParserChainNodeTestBase {

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

    private PowerParserChainNode powerParserChainNode;

    @BeforeEach
    void setUp() {
        powerParserChainNode = new PowerParserChainNode();
        powerParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.POW)).thenReturn(false);

        Expression expression = powerParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + powerParserChainNode + " to return next node's" +
                " result when doesn't match '**', got " + expression);
    }

    @Test
    void shouldReturnBinaryExpressionWithPowerOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.POW)).thenReturn(true).thenReturn(false);

        Expression expression = powerParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.POW);

        verify(tokenHolder, times(2)).match(TokenType.POW);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnNestedBinaryExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.POW)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = powerParserChainNode.parse(tokenHolder);

        BinaryExpression binaryExpression = assertBinaryExpression(expression);

        Expression left = binaryExpression.getLeft();

        BinaryExpression leftBinaryExpression =
                assertBinaryExpressionWithChildren(left, firstResult, secondResult, BinaryExpression.Operator.POW);

        assertBinaryExpressionChildren(binaryExpression, leftBinaryExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.POW);
        verify(next, times(3)).parse(tokenHolder);
    }
}
