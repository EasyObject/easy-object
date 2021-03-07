/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception.runtime;

public class NotCallableException extends InterpreterRuntimeException {

    private final String name;

    public NotCallableException(String name) {
        this.name = name;
    }

    @Override
    public String getUserMessage() {
        return name + " is not callable";
    }
}
