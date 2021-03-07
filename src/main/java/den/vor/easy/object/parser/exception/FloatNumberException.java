/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

public class FloatNumberException extends ParserException {

    @Override
    public String getUserMessage() {
        return "Invalid float number: didn't expect two dots";
    }
}
