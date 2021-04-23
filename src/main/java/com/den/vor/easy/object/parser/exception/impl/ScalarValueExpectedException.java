/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.exception.impl;

import com.den.vor.easy.object.parser.ast.Expression;
import com.den.vor.easy.object.value.ScalarValue;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.parser.exception.ExpressionLanguageException;

/**
 * Exception that is expected to be thrown when an {@link Expression},
 * when it expected a {@link ScalarValue} and got anything else.
 */
public class ScalarValueExpectedException extends ExpressionLanguageException {

    private final Value<?> actual;

    public ScalarValueExpectedException(Value<?> actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "ScalarValueExpected{" +
                "actual=" + actual +
                '}';
    }
}
