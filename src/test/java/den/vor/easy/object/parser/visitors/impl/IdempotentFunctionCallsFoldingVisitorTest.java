/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.visitors.impl;

import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.FunctionalValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdempotentFunctionCallsFoldingVisitorTest {

    @Mock
    private Variables variables;
    @Mock
    private Value<?> value;
    @Mock
    private FunctionalValue<?> functionalValue;
    @Mock
    private Value<?> evaluationResult;

    @InjectMocks
    private IdempotentFunctionCallsFoldingVisitor visitor;

    @Test
    void shouldReturnFunctionInvocation_whenExpressionIsNotValue(@Mock FunctionInvocationExpression expression,
                                                                 @Mock Expression nested) {
        when(expression.getExpression()).thenReturn(nested);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression, only()).getExpression();
    }

    @Test
    void shouldReturnFunctionInvocation_whenExpressionIsNotFunction(@Mock FunctionInvocationExpression expression,
                                                                    @Mock ValueExpression nested) {
        when(expression.getExpression()).thenReturn(nested);
        doReturn(value).when(nested).getValue();

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression, only()).getExpression();
    }

    @Test
    void shouldReturnFunctionInvocation_whenExpressionIsNotIdempotent(@Mock FunctionInvocationExpression expression,
                                                                      @Mock ValueExpression nested) {
        when(expression.getExpression()).thenReturn(nested);
        doReturn(functionalValue).when(nested).getValue();
        when(functionalValue.isIdempotent()).thenReturn(false);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression, only()).getExpression();
    }

    @Test
    void shouldReturnFunctionInvocation_whenNotAllArgsAreValues(@Mock FunctionInvocationExpression expression,
                                                                @Mock ValueExpression nested,
                                                                @Mock Expression arg) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getArgs()).thenReturn(List.of(arg));
        doReturn(functionalValue).when(nested).getValue();
        when(functionalValue.isIdempotent()).thenReturn(true);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);

        verify(expression).getExpression();
        verify(expression).getArgs();
        verify(functionalValue, only()).isIdempotent();
    }

    @Test
    void shouldReturnFunctionInvocation_whenNotAllArgsAreValues(@Mock FunctionInvocationExpression expression,
                                                                @Mock ValueExpression nested,
                                                                @Mock ValueExpression arg) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getArgs()).thenReturn(List.of(arg));
        doReturn(functionalValue).when(nested).getValue();
        doReturn(evaluationResult).when(expression).eval(variables);
        when(functionalValue.isIdempotent()).thenReturn(true);

        Expression result = visitor.visit(expression);
        assertValueExpression(result, evaluationResult);

        verify(expression).getExpression();
        verify(expression).getArgs();
        verify(functionalValue, only()).isIdempotent();
    }

    @Test
    void shouldReturnMethodInvocation_whenExpressionIsNotValue(@Mock MethodInvocationExpression expression,
                                                               @Mock Expression nested) {
        when(expression.getExpression()).thenReturn(nested);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression, only()).getExpression();
    }

    @Test
    void shouldReturnMethodInvocation_whenMethodIsNotValue(@Mock MethodInvocationExpression expression,
                                                           @Mock ValueExpression nested,
                                                           @Mock Expression method) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getMethod()).thenReturn(method);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression).getExpression();
        verify(expression).getMethod();
    }

    @Test
    void shouldReturnMethodInvocation_whenExpressionIsNotFunction(@Mock MethodInvocationExpression expression,
                                                                  @Mock ValueExpression nested,
                                                                  @Mock ValueExpression method) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getMethod()).thenReturn(method);
        doReturn(value).when(method).getValue();

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression).getExpression();
        verify(expression).getMethod();
    }

    @Test
    void shouldReturnMethodInvocation_whenExpressionIsNotIdempotent(@Mock MethodInvocationExpression expression,
                                                                    @Mock ValueExpression nested,
                                                                    @Mock ValueExpression method) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getMethod()).thenReturn(method);
        doReturn(functionalValue).when(method).getValue();
        when(functionalValue.isIdempotent()).thenReturn(false);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);
        verify(expression).getExpression();
        verify(expression).getMethod();
    }

    @Test
    void shouldReturnMethodInvocation_whenNotAllArgsAreValues(@Mock MethodInvocationExpression expression,
                                                              @Mock ValueExpression nested,
                                                              @Mock ValueExpression method,
                                                              @Mock Expression arg) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getMethod()).thenReturn(method);
        when(expression.getArgs()).thenReturn(List.of(arg));
        doReturn(functionalValue).when(method).getValue();
        when(functionalValue.isIdempotent()).thenReturn(true);

        Expression result = visitor.visit(expression);
        assertSame(expression, result, () -> "Expected visitor to return original expression, got " + result);

        verify(expression).getExpression();
        verify(expression).getMethod();
        verify(expression).getArgs();
        verify(functionalValue, only()).isIdempotent();
    }

    @Test
    void shouldReturnMethodInvocation_whenNotAllArgsAreValues(@Mock MethodInvocationExpression expression,
                                                              @Mock ValueExpression nested,
                                                              @Mock ValueExpression method,
                                                              @Mock ValueExpression arg) {
        when(expression.getExpression()).thenReturn(nested);
        when(expression.getMethod()).thenReturn(method);
        when(expression.getArgs()).thenReturn(List.of(arg));
        doReturn(functionalValue).when(method).getValue();
        doReturn(evaluationResult).when(expression).eval(variables);
        when(functionalValue.isIdempotent()).thenReturn(true);

        Expression result = visitor.visit(expression);
        assertValueExpression(result, evaluationResult);

        verify(expression).getExpression();
        verify(expression).getMethod();
        verify(expression).getArgs();
        verify(functionalValue, only()).isIdempotent();
    }

    private void assertValueExpression(Expression expression, Value<?> expectedValue) {
        assertTrue(expression instanceof ValueExpression);
        Value<?> actual = ((ValueExpression) expression).getValue();
        assertEquals(expectedValue, actual, () -> "Expected " + expression + " to have " + expectedValue +
                " inside. Got " + actual);
    }
}
