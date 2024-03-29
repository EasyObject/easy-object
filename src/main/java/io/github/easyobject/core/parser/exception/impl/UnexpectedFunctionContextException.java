/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.exception.impl;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.parser.exception.ExpressionLanguageException;

/**
 * Exception that is expected to be thrown when function doesn't expect a context, but gets it,
 * or vise versa, when it expects a context and gets {@code null}.
 */
public class UnexpectedFunctionContextException extends ExpressionLanguageException {

    private final Value<?> expected;
    private final Value<?> actual;

    public UnexpectedFunctionContextException(Value<?> expected, Value<?> actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "UnexpectedFunctionContextException{" +
                "expected=" + expected +
                ", actual=" + actual +
                '}';
    }
}
