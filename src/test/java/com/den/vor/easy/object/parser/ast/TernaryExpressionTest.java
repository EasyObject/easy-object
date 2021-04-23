/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.ast;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.BooleanValue;
import com.den.vor.easy.object.value.impl.IntValue;
import com.den.vor.easy.object.parser.visitors.ResultVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TernaryExpressionTest {

    @Mock
    private Expression condition;
    @Mock
    private Expression thenExpression;
    @Mock
    private Expression elseExpression;
    @Mock
    private Variables params;

    private TernaryExpression ternaryExpression;

    @BeforeEach
    void setUp() {
        ternaryExpression = new TernaryExpression(condition, thenExpression, elseExpression);
    }

    @Test
    void shouldEvalConditionAndThenExpr_whenConditionIsTrue() {
        IntValue expected = IntValue.of(1);

        Mockito.doReturn(BooleanValue.TRUE).when(condition).eval(params);
        doReturn(expected).when(thenExpression).eval(params);

        Value<?> result = ternaryExpression.eval(params);

        assertEquals(expected, result, () -> "Expected conditional expression to return thenExpression result (" +
                expected + "), got " + result);
        verify(condition, only()).eval(params);
        verify(thenExpression, only()).eval(params);
        verifyNoInteractions(elseExpression);
    }

    @Test
    void shouldEvalConditionAndElseExpr_whenConditionIsFalse() {
        IntValue expected = IntValue.of(1);

        doReturn(BooleanValue.FALSE).when(condition).eval(params);
        doReturn(expected).when(elseExpression).eval(params);

        Value<?> result = ternaryExpression.eval(params);

        assertEquals(expected, result, () -> "Expected conditional expression to return elseExpression result (" +
                expected + "), got " + result);
        verify(condition, only()).eval(params);
        verify(elseExpression, only()).eval(params);
        verifyNoInteractions(thenExpression);
    }

    @Test
    void shouldThrowException_whenCanNotCastConditionToBoolean() {
        doReturn(IntValue.of(1)).when(condition).eval(params);

        assertThrows(ClassCastException.class, () -> ternaryExpression.eval(params));
    }

    @Test
    void shouldCallVisitorVisitMethod_whenAcceptCalled(@Mock ResultVisitor<Object> resultVisitor) {
        Object visitorResponse = new Object();
        when(resultVisitor.visit(ternaryExpression)).thenReturn(visitorResponse);

        Object result = ternaryExpression.accept(resultVisitor);

        assertSame(visitorResponse, result, () -> "Expected ternaryExpression to return result from resultVisitor (" +
                visitorResponse + "), got" + result);
        verify(resultVisitor, only()).visit(ternaryExpression);
        verifyNoInteractions(condition, thenExpression, elseExpression);
    }
}
