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
import den.vor.easy.object.parser.ast.ConditionalExpression;
import den.vor.easy.object.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConditionalParserChainNodeTest extends ParserChainNodeTestBase {

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

    private ConditionalParserChainNode conditionalParserChainNode;

    @BeforeEach
    void setUp() {
        conditionalParserChainNode = new ConditionalParserChainNode();
        conditionalParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    public void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(false);
        when(tokenHolder.match(TokenType.LTEQ)).thenReturn(false);
        when(tokenHolder.match(TokenType.GT)).thenReturn(false);
        when(tokenHolder.match(TokenType.GTEQ)).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + conditionalParserChainNode + " to return next " +
                "node's result when doesn't match '<', '>', '<=' or '>=', got " + expression);
    }

    @Test
    public void shouldReturnBinaryExpressionWithLtOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(true).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.LT);

        verify(tokenHolder, times(2)).match(TokenType.LT);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    public void shouldReturnBinaryExpressionWithLteOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(false);
        when(tokenHolder.match(TokenType.LTEQ)).thenReturn(true).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.LTEQ);

        verify(tokenHolder, times(2)).match(TokenType.LTEQ);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    public void shouldReturnBinaryExpressionWithGtOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(false);
        when(tokenHolder.match(TokenType.LTEQ)).thenReturn(false);
        when(tokenHolder.match(TokenType.GT)).thenReturn(true).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.GT);

        verify(tokenHolder, times(2)).match(TokenType.GT);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    public void shouldReturnBinaryExpressionWithGteOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(false);
        when(tokenHolder.match(TokenType.LTEQ)).thenReturn(false);
        when(tokenHolder.match(TokenType.GT)).thenReturn(false);
        when(tokenHolder.match(TokenType.GTEQ)).thenReturn(true).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.GTEQ);

        verify(tokenHolder, times(2)).match(TokenType.GTEQ);
        verify(next, times(2)).parse(tokenHolder);
    }


    @Test
    public void shouldReturnNestedConditionalExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.LT)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = conditionalParserChainNode.parse(tokenHolder);

        ConditionalExpression conditionalExpression = assertConditionalExpression(expression);

        Expression left = conditionalExpression.getLeft();

        ConditionalExpression leftConditionalExpression = assertConditionalExpressionWithChildren(left, firstResult,
                secondResult, ConditionalExpression.Operation.LT);

        assertConditionalExpressionChildren(conditionalExpression, leftConditionalExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.LT);
        verify(next, times(3)).parse(tokenHolder);
    }
}
