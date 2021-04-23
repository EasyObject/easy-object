/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.exception.impl;

import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.exception.ExpressionLanguageException;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.FunctionalValue;

/**
 * Exception that is expected to be thrown when an {@link Expression},
 * when it expected a {@link FunctionalValue} and got anything else.
 */
public class FunctionalValueExpectedException extends ExpressionLanguageException {

    private final Value<?> actual;

    public FunctionalValueExpectedException(Value<?> actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "FunctionalValueExpected{" +
                "actual=" + actual +
                '}';
    }
}
