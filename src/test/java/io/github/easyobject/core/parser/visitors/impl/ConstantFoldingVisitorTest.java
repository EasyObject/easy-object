/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors.impl;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.parser.ast.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConstantFoldingVisitorTest {

    @Mock
    private Variables variables;

    @Mock
    private Expression expression1;
    @Mock
    private Expression expression2;
    @Mock
    private Value<Object> expressionResult;
    @Mock
    private ValueExpression valueExpression1;
    @Mock
    private ValueExpression valueExpression2;

    @InjectMocks
    private ConstantFoldingVisitor constantFoldingVisitor;

    @Test
    void shouldReturnUnaryExpressionAsIs_whenItIsNotValue(@Mock UnaryExpression unaryExpression) {
        when(unaryExpression.getExpression()).thenReturn(expression1);
        Expression result = constantFoldingVisitor.visit(unaryExpression);

        assertSame(unaryExpression, result, () -> "Expected visitor to return unary expression, got " + result);
        verify(unaryExpression, only()).getExpression();
    }

    @Test
    void shouldEvaluateUnaryExpression_whenNestedIsValue(@Mock UnaryExpression unaryExpression) {
        when(unaryExpression.getExpression()).thenReturn(valueExpression1);
        doReturn(expressionResult).when(unaryExpression).eval(variables);
        Expression result = constantFoldingVisitor.visit(unaryExpression);

        assertValueExpression(result, expressionResult);
        verify(unaryExpression).getExpression();
        verify(unaryExpression).eval(variables);
    }

    @Test
    void shouldReturnBinaryExpressionAsIs_whenLeftOperandNotValue(@Mock BinaryExpression binaryExpression) {
        when(binaryExpression.getLeft()).thenReturn(expression1);
        Expression result = constantFoldingVisitor.visit(binaryExpression);

        assertSame(binaryExpression, result, () -> "Expected visitor to return binary expression, got " + result);
        verify(binaryExpression).getLeft();
        verifyNoMoreInteractions(binaryExpression);
    }

    @Test
    void shouldReturnBinaryExpressionAsIs_whenRightOperandNotValue(@Mock BinaryExpression binaryExpression) {
        when(binaryExpression.getLeft()).thenReturn(valueExpression1);
        when(binaryExpression.getRight()).thenReturn(expression2);
        Expression result = constantFoldingVisitor.visit(binaryExpression);

        assertSame(binaryExpression, result, () -> "Expected visitor to return binary expression, got " + result);
        verify(binaryExpression).getLeft();
        verify(binaryExpression).getRight();
        verifyNoMoreInteractions(binaryExpression);
    }

    @Test
    void shouldEvaluateBinaryExpression_whenNestedExpressionsAreValue(@Mock BinaryExpression binaryExpression) {
        when(binaryExpression.getLeft()).thenReturn(valueExpression1);
        when(binaryExpression.getRight()).thenReturn(valueExpression2);
        doReturn(expressionResult).when(binaryExpression).eval(variables);

        Expression result = constantFoldingVisitor.visit(binaryExpression);

        assertValueExpression(result, expressionResult);
        verify(binaryExpression).getLeft();
        verify(binaryExpression).getRight();
        verify(binaryExpression).eval(variables);
    }

    @Test
    void shouldReturnConditionalExpressionAsIs_whenLeftOperandNotValue(@Mock ConditionalExpression expression) {
        when(expression.getLeft()).thenReturn(expression1);
        Expression result = constantFoldingVisitor.visit(expression);

        assertSame(expression, result, () -> "Expected visitor to return conditional expression, got " + result);
        verify(expression).getLeft();
        verifyNoMoreInteractions(expression);
    }

    @Test
    void shouldReturnConditionalExpressionAsIs_whenRightOperandNotValue(@Mock ConditionalExpression expression) {
        when(expression.getLeft()).thenReturn(valueExpression1);
        when(expression.getRight()).thenReturn(expression2);
        Expression result = constantFoldingVisitor.visit(expression);

        assertSame(expression, result, () -> "Expected visitor to return conditional expression, got " + result);
        verify(expression).getLeft();
        verify(expression).getRight();
        verifyNoMoreInteractions(expression);
    }

    @Test
    void shouldEvaluateConditionalExpression_whenNestedExpressionsAreValue(@Mock ConditionalExpression expression) {
        when(expression.getLeft()).thenReturn(valueExpression1);
        when(expression.getRight()).thenReturn(valueExpression2);
        doReturn(expressionResult).when(expression).eval(variables);

        Expression result = constantFoldingVisitor.visit(expression);

        assertValueExpression(result, expressionResult);
        verify(expression).getLeft();
        verify(expression).getRight();
        verify(expression).eval(variables);
    }

    private void assertValueExpression(Expression expression, Value<?> expectedValue) {
        assertTrue(expression instanceof ValueExpression);
        Value<?> actual = ((ValueExpression) expression).getValue();
        assertEquals(expectedValue, actual, () -> "Expected " + expression + " to have " + expectedValue +
                " inside. Got " + actual);
    }
}
