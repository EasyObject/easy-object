/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.impl;

import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.value.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ParserChainNodeTestBase {

    protected BinaryExpression assertBinaryExpressionWithChildren(Expression expression,
                                                                  Expression left,
                                                                  Expression right,
                                                                  BinaryExpression.Operation operation) {
        BinaryExpression binaryExpression = assertBinaryExpression(expression);
        assertBinaryExpressionChildren(binaryExpression, left, right);
        assertEquals(operation, binaryExpression.getOperation(), () -> "Expected " + binaryExpression +
                " to have " + operation + " operation, actual=" + binaryExpression.getOperation());
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
                                                             UnaryExpression.Operation operation) {
        UnaryExpression unaryExpression = assertUnaryExpression(expression);
        assertEquals(child, unaryExpression.getExpression(), () -> "Expected " + child +
                " to be the child in " + unaryExpression + ", got " + unaryExpression.getExpression());
        assertEquals(operation, unaryExpression.getOperation(), () -> "Expected " + unaryExpression +
                " to have " + operation + " operation, actual=" + unaryExpression.getOperation());
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
                                                                            ConditionalExpression.Operation operation) {
        ConditionalExpression conditionalExpression = assertConditionalExpression(expression);
        assertConditionalExpressionChildren(conditionalExpression, left, right);
        assertEquals(operation, conditionalExpression.getOperation(), () -> "Expected " + conditionalExpression +
                " to have " + operation + " operation, actual=" + conditionalExpression.getOperation());
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
