/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

public class InvalidNameException extends ParserException {

    private final String name;

    public InvalidNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUserMessage() {
        return "Invalid name [" + name + "]";
    }
}
