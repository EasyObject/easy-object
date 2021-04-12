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

/**
 * Exception that is thrown by functions when they get an unexpected number of arguments.
 */
public class WrongNumberOfFunctionArgumentsException extends ExpressionLanguageException {

    private final int expected;
    private final int actual;

    public WrongNumberOfFunctionArgumentsException(int expected, int actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "WrongNumberOfFunctionArguments{" +
                "expected=" + expected +
                ", actual=" + actual +
                '}';
    }
}
