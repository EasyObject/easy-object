/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.impl.tokenizer;

import io.github.easyobject.core.parser.InputHolder;
import io.github.easyobject.core.parser.Token;
import io.github.easyobject.core.parser.TokenType;
import org.mockito.stubbing.OngoingStubbing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TokenizerTestBase {

    public void assertToken(Token token, TokenType expectedTokenType, String expectedText) {
        assertEquals(expectedTokenType, token.getType(), () -> "Expected tokenizer to return " + expectedTokenType +
                " token, got " + token.getType());
        assertEquals(expectedText, token.getText(), () -> "Expected tokenizer to return token with text '" +
                expectedText + "', got " + token.getText());
    }

    public void initializeInputHolderToReturnText(InputHolder inputHolder, String text) {
        OngoingStubbing<Character> inputHolderStub = when(inputHolder.next());
        char[] chars = text.toCharArray();
        for (char c : chars) {
            inputHolderStub = inputHolderStub.thenReturn(c);
        }
    }
}
