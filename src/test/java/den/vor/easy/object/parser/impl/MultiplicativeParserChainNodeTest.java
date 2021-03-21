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
import den.vor.easy.object.parser.TokenType;
import den.vor.easy.object.parser.ast.BinaryExpression;
import den.vor.easy.object.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultiplicativeParserChainNodeTest extends ParserChainNodeTestBase {

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

    private MultiplicativeParserChainNode multiplicativeParserChainNode;

    @BeforeEach
    void setUp() {
        multiplicativeParserChainNode = new MultiplicativeParserChainNode();
        multiplicativeParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);

        Expression expression = multiplicativeParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + multiplicativeParserChainNode + " to return next " +
                "node's result when doesn't match '+' or '-', got " + expression);
    }

    @Test
    void shouldReturnBinaryExpressionWithMultiplyOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.STAR)).thenReturn(true).thenReturn(false);
        when(tokenHolder.match(TokenType.SLASH)).thenReturn(false);
        when(tokenHolder.match(TokenType.PERCENT)).thenReturn(false);

        Expression expression = multiplicativeParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.MULTIPLY);

        verify(tokenHolder, times(2)).match(TokenType.STAR);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnBinaryExpressionWithDivideOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.SLASH)).thenReturn(true).thenReturn(false);
        when(tokenHolder.match(TokenType.STAR)).thenReturn(false);
        when(tokenHolder.match(TokenType.PERCENT)).thenReturn(false);

        Expression expression = multiplicativeParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.DIVIDE);

        verify(tokenHolder, times(2)).match(TokenType.SLASH);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnBinaryExpressionWithRemainderOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.PERCENT)).thenReturn(true).thenReturn(false);
        when(tokenHolder.match(TokenType.STAR)).thenReturn(false);
        when(tokenHolder.match(TokenType.SLASH)).thenReturn(false);

        Expression expression = multiplicativeParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.REMAINDER);

        verify(tokenHolder, times(2)).match(TokenType.PERCENT);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnNestedBinaryExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.STAR)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = multiplicativeParserChainNode.parse(tokenHolder);

        BinaryExpression binaryExpression = assertBinaryExpression(expression);

        Expression left = binaryExpression.getLeft();

        BinaryExpression leftBinaryExpression = assertBinaryExpressionWithChildren(left, firstResult, secondResult,
                BinaryExpression.Operator.MULTIPLY);

        assertBinaryExpressionChildren(binaryExpression, leftBinaryExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.STAR);
        verify(next, times(3)).parse(tokenHolder);
    }
}
