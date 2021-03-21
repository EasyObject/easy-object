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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BitwiseXorParserChainNodeTest extends ParserChainNodeTestBase {

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

    private BitwiseXorParserChainNode bitwiseXorParserChainNode;

    @BeforeEach
    void setUp() {
        bitwiseXorParserChainNode = new BitwiseXorParserChainNode();
        bitwiseXorParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.CARET)).thenReturn(false);

        Expression expression = bitwiseXorParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + bitwiseXorParserChainNode + " to return next node's" +
                " result when doesn't match '^', got " + expression);
    }

    @Test
    void shouldReturnBinaryExpressionWithAndOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult);
        when(tokenHolder.match(TokenType.CARET)).thenReturn(true).thenReturn(false);

        Expression expression = bitwiseXorParserChainNode.parse(tokenHolder);

        assertBinaryExpressionWithChildren(expression, firstResult, secondResult, BinaryExpression.Operator.XOR);

        verify(tokenHolder, times(2)).match(TokenType.CARET);
        verify(next, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnNestedBinaryExpressions() {
        when(next.parse(tokenHolder)).thenReturn(firstResult).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.CARET)).thenReturn(true).thenReturn(true).thenReturn(false);

        Expression expression = bitwiseXorParserChainNode.parse(tokenHolder);

        BinaryExpression binaryExpression = assertBinaryExpression(expression);

        Expression left = binaryExpression.getLeft();

        BinaryExpression leftBinaryExpression =
                assertBinaryExpressionWithChildren(left, firstResult, secondResult, BinaryExpression.Operator.XOR);

        assertBinaryExpressionChildren(binaryExpression, leftBinaryExpression, thirdResult);

        verify(tokenHolder, times(3)).match(TokenType.CARET);
        verify(next, times(3)).parse(tokenHolder);
    }
}
