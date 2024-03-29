/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.Value;

import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTestHelper {

    private Value<?> left;
    private Value<?> right;
    private BinaryOperator<Value<?>> function;
    private String functionSymbol;

    static OperatorTestHelper test(Value<?> left, Value<?> right) {
        return new OperatorTestHelper().withLeft(left).withRight(right);
    }

    void verifyEquals(Value<?> expected) {
        Value<?> result = function.apply(left, right);
        Object value = result.getValue();

        assertTrue(expected.getValue().getClass().isInstance(value),
                () -> "Expected " + left + functionSymbol + right + " to return " + expected.getValue().getClass()
                        + ", got - " + value);
        assertEquals(expected.getValue(), value, () -> "Expected " + left + functionSymbol + right + " to return " +
                expected.getValue() + ", got - " + value);
    }

    public OperatorTestHelper withLeft(Value<?> left) {
        this.left = left;
        return this;
    }

    public OperatorTestHelper withRight(Value<?> right) {
        this.right = right;
        return this;
    }

    public OperatorTestHelper withFunction(BinaryOperator<Value<?>> function) {
        this.function = function;
        return this;
    }

    public OperatorTestHelper withFunctionSymbol(String functionSymbol) {
        this.functionSymbol = functionSymbol;
        return this;
    }
}
