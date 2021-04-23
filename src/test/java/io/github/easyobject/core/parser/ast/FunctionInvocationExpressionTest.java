/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.ast;

import io.github.easyobject.core.parser.visitors.ResultVisitor;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.FunctionalValue;
import io.github.easyobject.core.parser.exception.impl.FunctionalValueExpectedException;
import io.github.easyobject.core.value.impl.NullValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionInvocationExpressionTest {

    @Mock
    private Expression expression;
    @Mock
    private Expression arg;
    @Mock
    private Value<?> value;
    @Mock
    private Value<?> functionInvocationResult;
    @Mock
    private FunctionalValue<?> functionalValue;
    @Mock
    private Variables variables;

    @Test
    void shouldThrowException_whenExpressionResultIsNotFunctionalValue(){
        FunctionInvocationExpression functionInvocationExpression =
                new FunctionInvocationExpression(expression, Collections.emptyList());
        doReturn(value).when(expression).eval(variables);

        assertThrows(FunctionalValueExpectedException.class, () -> functionInvocationExpression.eval(variables));
        verify(expression, only()).eval(variables);
    }

    @Test
    void shouldEvaluateArgumentsAndFunction_whenExpressionResultIsFunctionalValue(){
        FunctionInvocationExpression functionInvocationExpression =
                new FunctionInvocationExpression(expression, List.of(arg));
        doReturn(functionalValue).when(expression).eval(variables);
        doReturn(value).when(arg).eval(variables);
        doReturn(functionInvocationResult).when(functionalValue).invoke(NullValue.NULL, List.of(value));
        Value<?> result = functionInvocationExpression.eval(variables);

        assertEquals(functionInvocationResult, result, () -> "Expected function to return" + functionInvocationResult
                + " got " + result);
        verify(expression, only()).eval(variables);
        verify(arg, only()).eval(variables);
        verify(functionalValue, only()).invoke(NullValue.NULL, List.of(value));
    }

    @Test
    void shouldCallVisitMethodOnVisitor_whenAccepted(@Mock ResultVisitor<Object> visitor,
                                                     @Mock FunctionInvocationExpression functionInvocationExpression) {
        Object expected = new Object();
        when(visitor.visit(any(FunctionInvocationExpression.class))).thenReturn(expected);
        when(functionInvocationExpression.accept(any())).thenCallRealMethod();

        Object result = functionInvocationExpression.accept(visitor);

        verify(visitor, only()).visit(functionInvocationExpression);
        verify(functionInvocationExpression, only()).accept(visitor);

        assertEquals(expected, result);
    }

}
