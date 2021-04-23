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
import com.den.vor.easy.object.parser.visitors.ResultVisitor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConditionalExpressionTest {

    @Mock
    private Value<Object> firstValue;
    @Mock
    private Value<Object> secondValue;
    @Mock
    private Value<Object> resultValue;
    @Mock
    private ValueExpression firstExpression;
    @Mock
    private ValueExpression secondExpression;
    @Mock
    private Variables variables;

    @Test
    void shouldEvaluateChildExpressionsAndCallAndMethodOnFirst() {
        doReturn(firstValue).when(firstExpression).eval(variables);
        doReturn(secondValue).when(secondExpression).eval(variables);

        ConditionalExpression binaryExpression = new ConditionalExpression(firstExpression, secondExpression,
                ConditionalExpression.Operator.AND);

        doReturn(resultValue).when(firstValue).and(secondValue);

        Value<?> result = binaryExpression.eval(variables);

        verify(firstExpression, only()).eval(variables);
        verify(secondExpression, only()).eval(variables);
        verify(firstValue, only()).and(secondValue);
        verifyNoInteractions(secondValue);

        assertEquals(resultValue, result);
    }

    @Test
    void shouldCallVisitMethodOnVisitor_whenAccepted(@Mock ResultVisitor<Object> visitor,
                                                            @Mock ConditionalExpression expression) {
        Object expected = new Object();
        when(visitor.visit(any(ConditionalExpression.class))).thenReturn(expected);
        when(expression.accept(any())).thenCallRealMethod();

        Object result = expression.accept(visitor);

        verify(visitor, only()).visit(expression);
        verify(expression, only()).accept(visitor);

        assertEquals(expected, result);
    }
}
