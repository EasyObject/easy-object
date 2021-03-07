/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

public class WrongTypeException extends ParserException {

    private final Class<?> expected;
    private final Class<?> actual;

    public WrongTypeException(Class<?> expected, Class<?> actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String getUserMessage() {
        return "Expected " + expected + ", got " + actual + " as an expression result";
    }

    @Override
    public String toString() {
        return getUserMessage();
    }
}
