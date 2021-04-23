/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.visitors;

import com.den.vor.easy.object.parser.ast.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbstractResultVisitorTest {

    @Mock
    private Object visitResult1;
    @Mock
    private Object visitResult2;
    @Mock
    private Object visitResult3;

    @Spy
    private AbstractResultVisitor<Object> visitor;

    @Test
    public void shouldVisitBothOperandsOfBinaryExpressionAndConcatResults(@Mock BinaryExpression binaryExpression,
                                                                          @Mock Expression left,
                                                                          @Mock Expression right) {
        when(binaryExpression.getLeft()).thenReturn(left);
        when(binaryExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(List.of(visitResult1));
        when(right.accept(visitor)).thenReturn(List.of(visitResult2));

        List<Object> result = visitor.visit(binaryExpression);

        assertEquals(List.of(visitResult1, visitResult2), result);
        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfConditionalAndConcatResults(@Mock ConditionalExpression conditionalExpression,
                                                                     @Mock Expression left,
                                                                     @Mock Expression right) {
        when(conditionalExpression.getLeft()).thenReturn(left);
        when(conditionalExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(List.of(visitResult1));
        when(right.accept(visitor)).thenReturn(List.of(visitResult2));

        List<Object> result = visitor.visit(conditionalExpression);

        assertEquals(List.of(visitResult1, visitResult2), result);
        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitNesterExpressionInUnaryAndConcatResults(@Mock UnaryExpression unaryExpression,
                                                                   @Mock Expression expression) {
        when(unaryExpression.getExpression()).thenReturn(expression);
        when(expression.accept(visitor)).thenReturn(List.of(visitResult1));

        List<Object> result = visitor.visit(unaryExpression);

        assertEquals(List.of(visitResult1), result);
        verify(expression, only()).accept(visitor);
    }

    @Test
    public void shouldReturnEmptyList_whenVisitingValueExpression(@Mock ValueExpression valueExpression) {
        List<Object> result = visitor.visit(valueExpression);

        assertTrue(result.isEmpty());
        verifyNoInteractions(valueExpression);
    }

    @Test
    public void shouldReturnEmptyList_whenContextAccessExpression(@Mock ContextVariableAccessExpression expression) {
        List<Object> result = visitor.visit(expression);

        assertTrue(result.isEmpty());
        verifyNoInteractions(expression);
    }

    @Test
    public void shouldVisitAllExpressionsOfTernaryAndConcatResults(@Mock TernaryExpression ternaryExpression,
                                                                   @Mock Expression condition,
                                                                   @Mock Expression thenExpression,
                                                                   @Mock Expression elseExpression) {
        when(ternaryExpression.getCondition()).thenReturn(condition);
        when(ternaryExpression.getThenExpression()).thenReturn(thenExpression);
        when(ternaryExpression.getElseExpression()).thenReturn(elseExpression);
        when(condition.accept(visitor)).thenReturn(List.of(visitResult1));
        when(thenExpression.accept(visitor)).thenReturn(List.of(visitResult2));
        when(elseExpression.accept(visitor)).thenReturn(List.of(visitResult3));

        List<Object> result = visitor.visit(ternaryExpression);

        assertEquals(List.of(visitResult1, visitResult2, visitResult3), result);
        verify(condition, only()).accept(visitor);
        verify(thenExpression, only()).accept(visitor);
        verify(elseExpression, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfMethodInvocationAndConcatResults(
            @Mock MethodInvocationExpression methodInvocationExpression,
            @Mock Expression expression,
            @Mock Expression method,
            @Mock Expression arg) {

        when(methodInvocationExpression.getMethod()).thenReturn(method);
        when(methodInvocationExpression.getExpression()).thenReturn(expression);
        when(methodInvocationExpression.getArgs()).thenReturn(List.of(arg));
        when(expression.accept(visitor)).thenReturn(List.of(visitResult1));
        when(method.accept(visitor)).thenReturn(List.of(visitResult2));
        when(arg.accept(visitor)).thenReturn(List.of(visitResult3));

        List<Object> result = visitor.visit(methodInvocationExpression);

        assertEquals(List.of(visitResult1, visitResult2, visitResult3), result);
        verify(expression, only()).accept(visitor);
        verify(method, only()).accept(visitor);
        verify(arg, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfFunctionInvocationAndConcatResults(
            @Mock FunctionInvocationExpression functionInvocationExpression,
            @Mock Expression expression,
            @Mock Expression arg) {

        when(functionInvocationExpression.getExpression()).thenReturn(expression);
        when(functionInvocationExpression.getArgs()).thenReturn(List.of(arg));
        when(expression.accept(visitor)).thenReturn(List.of(visitResult1));
        when(arg.accept(visitor)).thenReturn(List.of(visitResult2));

        List<Object> result = visitor.visit(functionInvocationExpression);

        assertEquals(List.of(visitResult1, visitResult2), result);
        verify(expression, only()).accept(visitor);
        verify(arg, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfVariableMapAccessExpressionAndConcatResults(
            @Mock VariableMapAccessExpression variableMapAccessExpression,
            @Mock Expression key1,
            @Mock Expression key2) {

        when(variableMapAccessExpression.getKeys()).thenReturn(List.of(key1, key2));
        when(key1.accept(visitor)).thenReturn(List.of(visitResult1));
        when(key2.accept(visitor)).thenReturn(List.of(visitResult2));

        List<Object> result = visitor.visit(variableMapAccessExpression);

        assertEquals(List.of(visitResult1, visitResult2), result);
        verify(key1, only()).accept(visitor);
        verify(key2, only()).accept(visitor);
    }
}
