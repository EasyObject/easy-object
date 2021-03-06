/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception.runtime;

public class TypeCastException extends InterpreterRuntimeException {

    private final String source;
    private final String target;

    public TypeCastException(String source, String target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "TypeCastException{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    @Override
    public String getUserMessage() {
        return "Unable to cast [" + source + "] to [" + target + "]";
    }
}
