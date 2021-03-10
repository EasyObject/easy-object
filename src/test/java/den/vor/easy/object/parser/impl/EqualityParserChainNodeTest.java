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
import den.vor.easy.object.parser.ast.ConditionalExpression;
import den.vor.easy.object.parser.ast.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EqualityParserChainNodeTest extends ParserChainNodeTestBase {

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

    private EqualityParserChainNode equalityParserChainNode;

    @BeforeEach
    void setUp() {
        equalityParserChainNode = new EqualityParserChainNode();
        equalityParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    public void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.EQEQ)).thenReturn(false);
        when(tokenHolder.match(TokenType.EXCLEQ)).thenReturn(false);

        Expression expression = equalityParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + equalityParserChainNode + " to return next " +
                "node's result when doesn't match '==' or '!=', got " + expression);
    }

    @Test
    public void shouldReturnBinaryExpressionWithEqOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.EQEQ)).thenReturn(true);

        Expression expression = equalityParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.EQUALS);

        verify(tokenHolder).match(TokenType.EQEQ);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    public void shouldReturnBinaryExpressionWithNotEqOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.EQEQ)).thenReturn(false);
        when(tokenHolder.match(TokenType.EXCLEQ)).thenReturn(true);

        Expression expression = equalityParserChainNode.parse(tokenHolder);

        assertConditionalExpressionWithChildren(expression, firstResult, secondResult,
                ConditionalExpression.Operation.NOT_EQUALS);

        verify(tokenHolder).match(TokenType.EXCLEQ);
        verify(next, times(2)).parse(tokenHolder);
    }
}
