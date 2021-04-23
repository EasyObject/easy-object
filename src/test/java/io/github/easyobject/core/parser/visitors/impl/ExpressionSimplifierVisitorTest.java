/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors.impl;

import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.ast.TernaryExpression;
import io.github.easyobject.core.parser.ast.ValueExpression;
import io.github.easyobject.core.parser.ast.Variables;
import io.github.easyobject.core.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpressionSimplifierVisitorTest {

    @Mock
    private Variables variables;
    @Mock
    private Expression expression;
    @Mock
    private Value<Object> expressionResult;
    @Mock
    private ValueExpression valueExpression;

    @InjectMocks
    private ExpressionSimplifierVisitor expressionSimplifierVisitor;

    @Test
    void shouldReturnTernaryExpressionAsIs_whenConditionNotValue(@Mock TernaryExpression ternaryExpression) {
        when(ternaryExpression.getCondition()).thenReturn(expression);
        Expression result = expressionSimplifierVisitor.visit(ternaryExpression);

        assertSame(ternaryExpression, result, () -> "Expected visitor to return unary expression, got " + result);
        verify(ternaryExpression, only()).getCondition();
    }

    @Test
    void shouldReturnTrueBranchOfTernaryExpression_whenConditionIsTrue(@Mock TernaryExpression ternaryExpression) {
        when(ternaryExpression.getCondition()).thenReturn(valueExpression);
        when(ternaryExpression.getThenExpression()).thenReturn(expression);
        when(ternaryExpression.getCondition()).thenReturn(valueExpression);
        doReturn(expressionResult).when(valueExpression).eval(variables);
        when(expressionResult.as(Boolean.class)).thenReturn(true);
        Expression result = expressionSimplifierVisitor.visit(ternaryExpression);

        assertSame(expression, result, () -> "Expected expression simplifier to return then branch, got " + result);
        verify(ternaryExpression).getCondition();
        verify(ternaryExpression).getThenExpression();
        verify(valueExpression).eval(variables);
        verifyNoMoreInteractions(ternaryExpression, valueExpression);
    }

    @Test
    void shouldReturnFalseBranchOfTernaryExpression_whenConditionIsFalse(@Mock TernaryExpression ternaryExpression) {
        when(ternaryExpression.getCondition()).thenReturn(valueExpression);
        when(ternaryExpression.getElseExpression()).thenReturn(expression);
        when(ternaryExpression.getCondition()).thenReturn(valueExpression);
        doReturn(expressionResult).when(valueExpression).eval(variables);
        when(expressionResult.as(Boolean.class)).thenReturn(false);
        Expression result = expressionSimplifierVisitor.visit(ternaryExpression);

        assertSame(expression, result, () -> "Expected expression simplifier to return then branch, got " + result);
        verify(ternaryExpression).getCondition();
        verify(ternaryExpression).getElseExpression();
        verify(valueExpression).eval(variables);
        verifyNoMoreInteractions(ternaryExpression, valueExpression);
    }

}
