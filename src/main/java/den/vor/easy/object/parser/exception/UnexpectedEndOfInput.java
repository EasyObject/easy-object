/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.exception;

public class UnexpectedEndOfInput extends ParserException {

    private char expectedChar;

    public UnexpectedEndOfInput(char expectedChar) {
        this.expectedChar = expectedChar;
    }

    @Override
    public String getUserMessage() {
        return "Expected " + expectedChar + ", got end of input";
    }
}
