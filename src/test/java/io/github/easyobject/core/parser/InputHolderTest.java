/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputHolderTest {

    @Test
    void shouldReturnFirstCharacter_whenPeekIsCalledMultipleTimes() {
        InputHolder inputHolder = new InputHolder("text");

        assertEquals('t', inputHolder.peek(),
                "Expected inputHolder to return the first character when peek is called");
        assertEquals('t', inputHolder.peek(),
                "Expected inputHolder to return the first character when peek is called");
        assertEquals('t', inputHolder.peek(),
                "Expected inputHolder to return the first character when peek is called");
    }

    @Test
    void shouldReturnCharactersInOrder_whenNextIsCalledMultipleTimes() {
        InputHolder inputHolder = new InputHolder("hi");

        assertEquals('i', inputHolder.next(),
                "Expected inputHolder to return the second character when the first next is called");
        assertEquals('\0', inputHolder.next(),
                "Expected inputHolder to return the empty char when the second next is called");
    }

    @Test
    void shouldReturnTrue_whenThereAreMoreChars() {
        InputHolder inputHolder = new InputHolder("h");

        assertTrue(inputHolder.hasMoreCharacters(),
                "Expected inputHolder initialized with non-empty string to have more characters");
    }

    @Test
    void shouldReturnFalse_whenThereAreNoChars() {
        InputHolder inputHolder = new InputHolder("");

        assertFalse(inputHolder.hasMoreCharacters(),
                "Expected inputHolder initialized with empty string not to have more characters");
    }

    @Test
    void shouldReturnFalse_whenAllCharsAreConsumed() {
        InputHolder inputHolder = new InputHolder("h");
        inputHolder.next();

        assertFalse(inputHolder.hasMoreCharacters(),
                "Expected inputHolder not to have more characters when all are consumed");
    }
}
