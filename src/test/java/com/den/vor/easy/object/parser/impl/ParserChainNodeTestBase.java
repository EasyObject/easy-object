/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.impl;

import com.den.vor.easy.object.parser.ast.*;
import com.den.vor.easy.object.value.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class ParserChainNodeTestBase {

    protected BinaryExpression assertBinaryExpressionWithChildren(Expression expression,
                                                                  Expression left,
                                                                  Expression right,
                                                                  BinaryExpression.Operator operator) {
        BinaryExpression binaryExpression = assertBinaryExpression(expression);
        assertBinaryExpressionChildren(binaryExpression, left, right);
        assertEquals(operator, binaryExpression.getOperator(), () -> "Expected " + binaryExpression +
                " to have " + operator + " operation, actual=" + binaryExpression.getOperator());
        return binaryExpression;
    }

    protected BinaryExpression assertBinaryExpression(Expression expression) {
        assertTrue(expression instanceof BinaryExpression, () -> "Expected ParserChainNode to return " +
                "BinaryExpression, got" + expression);
        return (BinaryExpression) expression;
    }

    protected ValueExpression assertValueExpression(Expression expression, Value<?> value) {
        assertTrue(expression instanceof ValueExpression, () -> "Expected ParserChainNode to return " +
                "ValueExpression, got" + expression);
        ValueExpression valueExpression = (ValueExpression) expression;
        assertEquals(value, valueExpression.getValue(), () -> "Expected ValueExpression=" + valueExpression + " " +
                "to have value=" + value + ", got - " + valueExpression.getValue());
        return valueExpression;
    }

    protected UnaryExpression assertUnaryExpression(Expression expression) {
        assertTrue(expression instanceof UnaryExpression, () -> "Expected ParserChainNode to return " +
                "UnaryExpression, got" + expression);
        return (UnaryExpression) expression;
    }

    protected UnaryExpression assertUnaryExpressionWithChild(Expression expression,
                                                             Expression child,
                                                             UnaryExpression.Operator operator) {
        UnaryExpression unaryExpression = assertUnaryExpression(expression);
        assertEquals(child, unaryExpression.getExpression(), () -> "Expected " + child +
                " to be the child in " + unaryExpression + ", got " + unaryExpression.getExpression());
        assertEquals(operator, unaryExpression.getOperator(), () -> "Expected " + unaryExpression +
                " to have " + operator + " operation, actual=" + unaryExpression.getOperator());
        return unaryExpression;
    }

    protected void assertBinaryExpressionChildren(BinaryExpression expression, Expression left, Expression right) {
        assertEquals(left, expression.getLeft(), () -> "Expected " + left +
                " to be the left operand in " + expression);
        assertEquals(right, expression.getRight(), () -> "Expected " + right +
                " to be the left operand in " + expression);
    }

    protected ConditionalExpression assertConditionalExpressionWithChildren(Expression expression,
                                                                            Expression left,
                                                                            Expression right,
                                                                            ConditionalExpression.Operator operator) {
        ConditionalExpression conditionalExpression = assertConditionalExpression(expression);
        assertConditionalExpressionChildren(conditionalExpression, left, right);
        assertEquals(operator, conditionalExpression.getOperator(), () -> "Expected " + conditionalExpression +
                " to have " + operator + " operation, actual=" + conditionalExpression.getOperator());
        return conditionalExpression;
    }

    protected ConditionalExpression assertConditionalExpression(Expression expression) {
        assertTrue(expression instanceof ConditionalExpression, () -> "Expected ParserChainNode to return " +
                "ConditionalExpression, got" + expression);
        return (ConditionalExpression) expression;
    }

    protected void assertConditionalExpressionChildren(ConditionalExpression expression, Expression left,
                                                       Expression right) {
        assertEquals(left, expression.getLeft(), () -> "Expected " + left +
                " to be the left operand in " + expression);
        assertEquals(right, expression.getRight(), () -> "Expected " + right +
                " to be the left operand in " + expression);
    }
}
