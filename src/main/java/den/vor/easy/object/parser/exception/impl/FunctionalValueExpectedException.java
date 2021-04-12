/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception.impl;

import den.vor.easy.object.parser.exception.ExpressionLanguageException;
import den.vor.easy.object.value.Value;

/**
 * Exception that is expected to be thrown when an {@link den.vor.easy.object.parser.ast.Expression},
 * when it expected a {@link den.vor.easy.object.value.impl.FunctionalValue} and got anything else.
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
