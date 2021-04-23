/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.visitors;

import io.github.easyobject.core.parser.ast.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DelegatingOptimizationVisitorTest {

    @Spy
    private DelegatingOptimizationVisitor visitor;
    @Mock
    private Expression expression;

    @Test
    public void shouldVisitBothOperandsOfBinaryExpressionAndReturnOldIfNotChanged(
            @Mock BinaryExpression binaryExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(binaryExpression.getLeft()).thenReturn(left);
        when(binaryExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(left);
        when(right.accept(visitor)).thenReturn(right);

        Expression result = visitor.visit(binaryExpression);

        assertSame(binaryExpression, result, () ->
                "Expected visitor to return same instance when operands changed, got " + result);

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfBinaryExpressionAndReturnNewExpressionIfLeftChanged(
            @Mock BinaryExpression binaryExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(binaryExpression.getLeft()).thenReturn(left);
        when(binaryExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(expression);
        when(right.accept(visitor)).thenReturn(right);

        Expression result = visitor.visit(binaryExpression);

        assertNotSame(binaryExpression, result, () ->
                "Expected visitor to return new instance when operands changed, got " + result);
        assertTrue(result instanceof BinaryExpression);
        BinaryExpression binaryResult = (BinaryExpression) result;
        assertEquals(right, binaryResult.getRight(),
                () -> "Expected right not to change, got " + binaryResult.getRight());
        assertEquals(expression, binaryResult.getLeft(), () -> "Expected result to have " + left +
                " visit result as left operand, got " + binaryResult.getLeft());

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfBinaryExpressionAndReturnNewExpressionIfRightChanged(
            @Mock BinaryExpression binaryExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(binaryExpression.getLeft()).thenReturn(left);
        when(binaryExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(left);
        when(right.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(binaryExpression);

        assertNotSame(binaryExpression, result, () ->
                "Expected visitor to return new instance when operands don't change, got " + result);
        assertTrue(result instanceof BinaryExpression);
        BinaryExpression binaryResult = (BinaryExpression) result;
        assertEquals(left, binaryResult.getLeft(),
                () -> "Expected left not to change, got " + binaryResult.getLeft());
        assertEquals(expression, binaryResult.getRight(), () -> "Expected result to have " + right +
                " visit result as right operand, got " + binaryResult.getRight());

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfConditionalExpressionAndReturnOldIfNotChanged(
            @Mock ConditionalExpression conditionalExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(conditionalExpression.getLeft()).thenReturn(left);
        when(conditionalExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(left);
        when(right.accept(visitor)).thenReturn(right);

        Expression result = visitor.visit(conditionalExpression);

        assertSame(conditionalExpression, result, () ->
                "Expected visitor to return same instance when operands changed, got " + result);

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfConditionalExpressionAndReturnNewExpressionIfLeftChanged(
            @Mock ConditionalExpression conditionalExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(conditionalExpression.getLeft()).thenReturn(left);
        when(conditionalExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(expression);
        when(right.accept(visitor)).thenReturn(right);

        Expression result = visitor.visit(conditionalExpression);

        assertNotSame(conditionalExpression, result, () ->
                "Expected visitor to return new instance when operands changed, got " + result);
        assertTrue(result instanceof ConditionalExpression);
        ConditionalExpression conditionalResult = (ConditionalExpression) result;
        assertEquals(right, conditionalResult.getRight(),
                () -> "Expected right not to change, got " + conditionalResult.getRight());
        assertEquals(expression, conditionalResult.getLeft(), () -> "Expected result to have " + left +
                " visit result as left operand, got " + conditionalResult.getLeft());

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitBothOperandsOfConditionalExpressionAndReturnNewExpressionIfRightChanged(
            @Mock ConditionalExpression conditionalExpression,
            @Mock Expression left,
            @Mock Expression right) {

        when(conditionalExpression.getLeft()).thenReturn(left);
        when(conditionalExpression.getRight()).thenReturn(right);
        when(left.accept(visitor)).thenReturn(left);
        when(right.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(conditionalExpression);

        assertNotSame(conditionalExpression, result, () ->
                "Expected visitor to return new instance when nested doesn't change, got " + result);
        assertTrue(result instanceof ConditionalExpression);
        ConditionalExpression conditionalResult = (ConditionalExpression) result;
        assertEquals(left, conditionalResult.getLeft(),
                () -> "Expected left not to change, got " + conditionalResult.getLeft());
        assertEquals(expression, conditionalResult.getRight(), () -> "Expected result to have " + right +
                " visit result as right operand, got " + conditionalResult.getRight());

        verify(left, only()).accept(visitor);
        verify(right, only()).accept(visitor);
    }

    @Test
    public void shouldVisitExpressionOfUnaryExpressionAndReturnOldIfNotChanged(@Mock UnaryExpression unaryExpression,
                                                                               @Mock Expression inner) {

        when(unaryExpression.getExpression()).thenReturn(inner);
        when(inner.accept(visitor)).thenReturn(inner);

        Expression result = visitor.visit(unaryExpression);

        assertSame(unaryExpression, result, () ->
                "Expected visitor to return same instance when operands don't change, got " + result);

        verify(inner, only()).accept(visitor);
    }

    @Test
    public void shouldVisitExpressionOfUnaryExpressionAndReturnNewExpressionIfChanged(
            @Mock UnaryExpression unaryExpression,
            @Mock Expression inner) {

        when(unaryExpression.getExpression()).thenReturn(inner);
        when(inner.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(unaryExpression);

        assertNotSame(unaryExpression, result, () ->
                "Expected visitor to return new instance when nested changed, got " + result);
        assertTrue(result instanceof UnaryExpression);
        UnaryExpression unaryResult = (UnaryExpression) result;
        assertEquals(expression, unaryResult.getExpression(), () -> "Expected result to have " + inner +
                " visit result as nested, got " + unaryResult.getExpression());

        verify(inner, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfTernaryExpressionAndReturnOldIfNotChanged(
            @Mock TernaryExpression ternaryExpression,
            @Mock Expression condition,
            @Mock Expression thenBranch,
            @Mock Expression elseBranch) {

        when(ternaryExpression.getCondition()).thenReturn(condition);
        when(ternaryExpression.getThenExpression()).thenReturn(thenBranch);
        when(ternaryExpression.getElseExpression()).thenReturn(elseBranch);
        when(condition.accept(visitor)).thenReturn(condition);
        when(thenBranch.accept(visitor)).thenReturn(thenBranch);
        when(elseBranch.accept(visitor)).thenReturn(elseBranch);

        Expression result = visitor.visit(ternaryExpression);

        assertSame(ternaryExpression, result, () ->
                "Expected visitor to return same instance when expressions didn't change, got " + result);

        verify(condition, only()).accept(visitor);
        verify(thenBranch, only()).accept(visitor);
        verify(elseBranch, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfTernaryAndReturnNewExpressionIfConditionChanged(
            @Mock TernaryExpression ternaryExpression,
            @Mock Expression condition,
            @Mock Expression thenBranch,
            @Mock Expression elseBranch) {

        when(ternaryExpression.getCondition()).thenReturn(condition);
        when(ternaryExpression.getThenExpression()).thenReturn(thenBranch);
        when(ternaryExpression.getElseExpression()).thenReturn(elseBranch);
        when(condition.accept(visitor)).thenReturn(expression);
        when(thenBranch.accept(visitor)).thenReturn(thenBranch);
        when(elseBranch.accept(visitor)).thenReturn(elseBranch);

        Expression result = visitor.visit(ternaryExpression);

        assertNotSame(ternaryExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof TernaryExpression);
        TernaryExpression ternaryResult = (TernaryExpression) result;
        assertEquals(thenBranch, ternaryResult.getThenExpression(),
                () -> "Expected then not to change, got " + ternaryResult.getThenExpression());
        assertEquals(elseBranch, ternaryResult.getElseExpression(),
                () -> "Expected else not to change, got " + ternaryResult.getElseExpression());
        assertEquals(expression, ternaryResult.getCondition(), () -> "Expected result to have " + condition +
                " visit result as condition, got " + ternaryResult.getCondition());

        verify(condition, only()).accept(visitor);
        verify(thenBranch, only()).accept(visitor);
        verify(elseBranch, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfTernaryAndReturnNewExpressionIfThenBranchChanged(
            @Mock TernaryExpression ternaryExpression,
            @Mock Expression condition,
            @Mock Expression thenBranch,
            @Mock Expression elseBranch) {

        when(ternaryExpression.getCondition()).thenReturn(condition);
        when(ternaryExpression.getThenExpression()).thenReturn(thenBranch);
        when(ternaryExpression.getElseExpression()).thenReturn(elseBranch);
        when(condition.accept(visitor)).thenReturn(condition);
        when(thenBranch.accept(visitor)).thenReturn(expression);
        when(elseBranch.accept(visitor)).thenReturn(elseBranch);

        Expression result = visitor.visit(ternaryExpression);

        assertNotSame(ternaryExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof TernaryExpression);
        TernaryExpression ternaryResult = (TernaryExpression) result;
        assertEquals(condition, ternaryResult.getCondition(),
                () -> "Expected condition not to change, got " + ternaryResult.getCondition());
        assertEquals(elseBranch, ternaryResult.getElseExpression(),
                () -> "Expected else not to change, got " + ternaryResult.getElseExpression());
        assertEquals(expression, ternaryResult.getThenExpression(), () -> "Expected result to have " + thenBranch +
                " visit result as then branch, got " + ternaryResult.getThenExpression());

        verify(condition, only()).accept(visitor);
        verify(thenBranch, only()).accept(visitor);
        verify(elseBranch, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfTernaryAndReturnNewExpressionIfElseBranchChanged(
            @Mock TernaryExpression ternaryExpression,
            @Mock Expression condition,
            @Mock Expression thenBranch,
            @Mock Expression elseBranch) {

        when(ternaryExpression.getCondition()).thenReturn(condition);
        when(ternaryExpression.getThenExpression()).thenReturn(thenBranch);
        when(ternaryExpression.getElseExpression()).thenReturn(elseBranch);
        when(condition.accept(visitor)).thenReturn(condition);
        when(thenBranch.accept(visitor)).thenReturn(thenBranch);
        when(elseBranch.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(ternaryExpression);

        assertNotSame(ternaryExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof TernaryExpression);
        TernaryExpression ternaryResult = (TernaryExpression) result;
        assertEquals(condition, ternaryResult.getCondition(),
                () -> "Expected condition not to change, got " + ternaryResult.getCondition());
        assertEquals(thenBranch, ternaryResult.getThenExpression(),
                () -> "Expected then not to change, got " + ternaryResult.getThenExpression());
        assertEquals(expression, ternaryResult.getElseExpression(), () -> "Expected result to have " + elseBranch +
                " visit result as else branch, got " + ternaryResult.getElseExpression());

        verify(condition, only()).accept(visitor);
        verify(thenBranch, only()).accept(visitor);
        verify(elseBranch, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfMethodInvocationAndReturnOldIfNotChanged(
            @Mock MethodInvocationExpression methodInvocationExpression,
            @Mock Expression expr,
            @Mock Expression method,
            @Mock Expression argument) {

        when(methodInvocationExpression.getExpression()).thenReturn(expr);
        when(methodInvocationExpression.getMethod()).thenReturn(method);
        when(methodInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expr);
        when(method.accept(visitor)).thenReturn(method);
        when(argument.accept(visitor)).thenReturn(argument);

        Expression result = visitor.visit(methodInvocationExpression);

        assertSame(methodInvocationExpression, result, () ->
                "Expected visitor to return same instance when expressions didn't change, got " + result);

        verify(expr, only()).accept(visitor);
        verify(method, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfMethodInvocationAndReturnNewExpressionIfExpressionChanged(
            @Mock MethodInvocationExpression methodInvocationExpression,
            @Mock Expression expr,
            @Mock Expression method,
            @Mock Expression argument) {

        when(methodInvocationExpression.getExpression()).thenReturn(expr);
        when(methodInvocationExpression.getMethod()).thenReturn(method);
        when(methodInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expression);
        when(method.accept(visitor)).thenReturn(method);
        when(argument.accept(visitor)).thenReturn(argument);

        Expression result = visitor.visit(methodInvocationExpression);

        assertNotSame(methodInvocationExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof MethodInvocationExpression);
        MethodInvocationExpression visitResult = (MethodInvocationExpression) result;
        assertEquals(method, visitResult.getMethod(),
                () -> "Expected method not to change, got " + visitResult.getMethod());
        assertEquals(argument, visitResult.getArgs().get(0),
                () -> "Expected argument not to change, got " + visitResult.getArgs().get(0));
        assertEquals(expression, visitResult.getExpression(), () -> "Expected result to have " + expression +
                " visit result as expression, got " + visitResult.getExpression());

        verify(expr, only()).accept(visitor);
        verify(method, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfMethodInvocationAndReturnNewExpressionIfMethodChanged(
            @Mock MethodInvocationExpression methodInvocationExpression,
            @Mock Expression expr,
            @Mock Expression method,
            @Mock Expression argument) {

        when(methodInvocationExpression.getExpression()).thenReturn(expr);
        when(methodInvocationExpression.getMethod()).thenReturn(method);
        when(methodInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expr);
        when(method.accept(visitor)).thenReturn(expression);
        when(argument.accept(visitor)).thenReturn(argument);

        Expression result = visitor.visit(methodInvocationExpression);

        assertNotSame(methodInvocationExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof MethodInvocationExpression);
        MethodInvocationExpression visitResult = (MethodInvocationExpression) result;
        assertEquals(expr, visitResult.getExpression(),
                () -> "Expected expression not to change, got " + visitResult.getExpression());
        assertEquals(argument, visitResult.getArgs().get(0),
                () -> "Expected argument not to change, got " + visitResult.getArgs().get(0));
        assertEquals(expression, visitResult.getMethod(), () -> "Expected result to have " + method +
                " visit result as method, got " + visitResult.getMethod());

        verify(expr, only()).accept(visitor);
        verify(method, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfMethodInvocationAndReturnNewExpressionIfArgChanged(
            @Mock MethodInvocationExpression methodInvocationExpression,
            @Mock Expression expr,
            @Mock Expression method,
            @Mock Expression argument) {

        when(methodInvocationExpression.getExpression()).thenReturn(expr);
        when(methodInvocationExpression.getMethod()).thenReturn(method);
        when(methodInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expr);
        when(method.accept(visitor)).thenReturn(method);
        when(argument.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(methodInvocationExpression);

        assertNotSame(methodInvocationExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof MethodInvocationExpression);
        MethodInvocationExpression visitResult = (MethodInvocationExpression) result;
        assertEquals(expr, visitResult.getExpression(),
                () -> "Expected expression not to change, got " + visitResult.getExpression());
        assertEquals(method, visitResult.getMethod(),
                () -> "Expected method not to change, got " + visitResult.getMethod());
        assertEquals(expression, visitResult.getArgs().get(0), () -> "Expected result to have " + argument +
                " visit result as argument, got " + visitResult.getArgs().get(0));

        verify(expr, only()).accept(visitor);
        verify(method, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }


    @Test
    public void shouldVisitAllExpressionsOfFunctionalAndReturnOldIfNotChanged(
            @Mock FunctionInvocationExpression functionInvocationExpression,
            @Mock Expression expr,
            @Mock Expression argument) {

        when(functionInvocationExpression.getExpression()).thenReturn(expr);
        when(functionInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expr);
        when(argument.accept(visitor)).thenReturn(argument);

        Expression result = visitor.visit(functionInvocationExpression);

        assertSame(functionInvocationExpression, result, () ->
                "Expected visitor to return same instance when expressions didn't change, got " + result);

        verify(expr, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfFunctionalAndReturnNewExpressionIfExpressionChanged(
            @Mock FunctionInvocationExpression functionInvocationExpression,
            @Mock Expression expr,
            @Mock Expression argument) {

        when(functionInvocationExpression.getExpression()).thenReturn(expr);
        when(functionInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expression);
        when(argument.accept(visitor)).thenReturn(argument);

        Expression result = visitor.visit(functionInvocationExpression);

        assertNotSame(functionInvocationExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof FunctionInvocationExpression);
        FunctionInvocationExpression visitResult = (FunctionInvocationExpression) result;
        assertEquals(argument, visitResult.getArgs().get(0),
                () -> "Expected argument not to change, got " + visitResult.getArgs().get(0));
        assertEquals(expression, visitResult.getExpression(), () -> "Expected result to have " + expression +
                " visit result as expression, got " + visitResult.getExpression());

        verify(expr, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitAllExpressionsOfFunctionalAndReturnNewExpressionIfArgChanged(
            @Mock FunctionInvocationExpression functionInvocationExpression,
            @Mock Expression expr,
            @Mock Expression argument) {

        when(functionInvocationExpression.getExpression()).thenReturn(expr);
        when(functionInvocationExpression.getArgs()).thenReturn(List.of(argument));
        when(expr.accept(visitor)).thenReturn(expr);
        when(argument.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(functionInvocationExpression);

        assertNotSame(functionInvocationExpression, result, () ->
                "Expected visitor to return new instance when expressions changed, got " + result);
        assertTrue(result instanceof FunctionInvocationExpression);
        FunctionInvocationExpression visitResult = (FunctionInvocationExpression) result;
        assertEquals(expr, visitResult.getExpression(),
                () -> "Expected expression not to change, got " + visitResult.getExpression());
        assertEquals(expression, visitResult.getArgs().get(0), () -> "Expected result to have " + argument +
                " visit result as argument, got " + visitResult.getArgs().get(0));

        verify(expr, only()).accept(visitor);
        verify(argument, only()).accept(visitor);
    }

    @Test
    public void shouldVisitKeyOfVariableAccessExpressionAndReturnOldIfNotChanged(
            @Mock VariableMapAccessExpression variableMapAccessExpression,
            @Mock Expression key) {

        when(variableMapAccessExpression.getKeys()).thenReturn(List.of(key));
        when(key.accept(visitor)).thenReturn(key);

        Expression result = visitor.visit(variableMapAccessExpression);

        assertSame(variableMapAccessExpression, result, () ->
                "Expected visitor to return same instance when operands don't change, got " + result);

        verify(key, only()).accept(visitor);
    }

    @Test
    public void shouldVisitKeyOfVariableAccessExpressionAndReturnNewExpressionIfChanged(
            @Mock VariableMapAccessExpression variableMapAccessExpression,
            @Mock Expression key) {

        when(variableMapAccessExpression.getKeys()).thenReturn(List.of(key));
        when(key.accept(visitor)).thenReturn(expression);

        Expression result = visitor.visit(variableMapAccessExpression);

        assertNotSame(variableMapAccessExpression, result, () ->
                "Expected visitor to return new instance when nested changed, got " + result);
        assertTrue(result instanceof VariableMapAccessExpression);
        VariableMapAccessExpression visitResult = (VariableMapAccessExpression) result;
        assertEquals(expression, visitResult.getKeys().get(0), () -> "Expected result to have " + key +
                " visit result as key, got " + visitResult.getKeys().get(0));

        verify(key, only()).accept(visitor);
    }
}
