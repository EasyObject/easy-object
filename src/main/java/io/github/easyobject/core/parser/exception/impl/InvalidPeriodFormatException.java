/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.exception.impl;

import io.github.easyobject.core.bean.Period;
import io.github.easyobject.core.parser.exception.ExpressionLanguageException;

/**
 * Exception that is thrown when {@link Period} format is not correct.
 * Example: {@code 1d3m.4}
 */
public class InvalidPeriodFormatException extends ExpressionLanguageException {

    private final String name;

    public InvalidPeriodFormatException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InvalidPeriodFormatException{" +
                "name='" + name + '\'' +
                '}';
    }
}
