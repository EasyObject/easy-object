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
 * Exception that is thrown by {@link den.vor.easy.object.parser.Lexer} when a floating point number contains two dots.
 * Example: {@code 1.2.3}.
 */
public class FloatNumberException extends ExpressionLanguageException {

}
