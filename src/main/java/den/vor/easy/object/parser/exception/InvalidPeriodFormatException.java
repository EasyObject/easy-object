/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

/**
 * Exception that is thrown when {@link den.vor.easy.object.bean.Period} format is not correct.
 * Example: {@code 1d3m.4}
 */
public class InvalidPeriodFormatException extends ELException {

    private final String name;

    public InvalidPeriodFormatException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
