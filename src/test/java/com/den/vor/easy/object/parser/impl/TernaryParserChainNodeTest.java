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
import com.den.vor.easy.object.parser.ast.Expression;
import com.den.vor.easy.object.parser.ast.TernaryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TernaryParserChainNodeTest extends ParserChainNodeTestBase {

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

    private TernaryParserChainNode ternaryParserChainNode;

    @BeforeEach
    void setUp() {
        ternaryParserChainNode = new TernaryParserChainNode();
        ternaryParserChainNode.setNext(next).setRoot(root);
    }

    @Test
    void shouldReturnNextResultWhenDoesNotMatchOperator() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(tokenHolder.match(TokenType.QUESTION_SIGN)).thenReturn(false);

        Expression expression = ternaryParserChainNode.parse(tokenHolder);

        assertEquals(firstResult, expression, () -> "Expected " + ternaryParserChainNode + " to return next " +
                "node's result when doesn't match '?', got " + expression);
    }

    @Test
    void shouldReturnTernaryExpression() {
        when(next.parse(tokenHolder)).thenReturn(firstResult);
        when(root.parse(tokenHolder)).thenReturn(secondResult).thenReturn(thirdResult);
        when(tokenHolder.match(TokenType.QUESTION_SIGN)).thenReturn(true);

        Expression expression = ternaryParserChainNode.parse(tokenHolder);

        assertTrue(expression instanceof TernaryExpression, () -> "Expected ParserChainNode to return " +
                "TernaryExpression, got" + expression);
        TernaryExpression ternaryExpression = (TernaryExpression) expression;

        assertEquals(firstResult, ternaryExpression.getCondition(), () -> "Expected " + firstResult + " to be " +
                "condition in TernaryExpression=" + ternaryExpression + ", got " + ternaryExpression.getCondition());
        assertEquals(secondResult, ternaryExpression.getThenExpression(), () -> "Expected " + secondResult + " to be " +
                "'then' in TernaryExpression=" + ternaryExpression + ", got " + ternaryExpression.getThenExpression());
        assertEquals(thirdResult, ternaryExpression.getElseExpression(), () -> "Expected " + thirdResult + " to be " +
                "'else' in TernaryExpression=" + ternaryExpression + ", got " + ternaryExpression.getElseExpression());

        verify(next).parse(tokenHolder);
        verify(root, times(2)).parse(tokenHolder);
    }
}
