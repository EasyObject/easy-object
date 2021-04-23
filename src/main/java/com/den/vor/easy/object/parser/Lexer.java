/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser;

import com.den.vor.easy.object.parser.impl.tokenizer.NumberTokenizer;
import com.den.vor.easy.object.parser.impl.tokenizer.OperatorTokenizer;
import com.den.vor.easy.object.parser.impl.tokenizer.TextTokenizer;
import com.den.vor.easy.object.parser.impl.tokenizer.WordTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that parses an input string into a collection of {@link Token}.
 */
public class Lexer {

    private String input;
    private final List<Token> tokens;
    private List<Tokenizer> tokenizers;

    public Lexer(String input) {
        this.input = input;
        tokens = new ArrayList<>();
        tokenizers = List.of(
                new NumberTokenizer(),
                new OperatorTokenizer(),
                new TextTokenizer(),
                new WordTokenizer()
        );
    }

    public TokenHolder tokenize() {
        InputHolder inputHolder = new InputHolder(input);
        while (inputHolder.hasMoreCharacters()) {
            char current = inputHolder.peek();
            Optional<Tokenizer> tokenizer = tokenizers.stream().filter(t -> t.supports(current)).findAny();
            if (tokenizer.isPresent()) {
                tokens.add(tokenizer.get().tokenize(inputHolder));
            } else {
                inputHolder.next(); // skip whitespaces
            }
        }
        return new TokenHolder(tokens);
    }
}
