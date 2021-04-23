/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser;

import io.github.easyobject.core.parser.ast.Expression;
import io.github.easyobject.core.parser.impl.*;

/**
 * Class that parses a collection of {@link Token} into an {@link Expression} tree.
 */
public final class Parser {

    /**
     * Chain of concrete parsers. {@linkplain Parser#parse()} delegates to it.
     */
    private ParserChain parserChain;
    private TokenHolder tokenHolder;

    public Parser(TokenHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
        parserChain = new ParserChain().andThen(new TernaryParserChainNode())
                .andThen(new LogicalOrParserChainNode())
                .andThen(new LogicalAndParserChainNode())
                .andThen(new BitwiseOrParserChainNode())
                .andThen(new BitwiseXorParserChainNode())
                .andThen(new BitwiseAndParserChainNode())
                .andThen(new EqualityParserChainNode())
                .andThen(new ConditionalParserChainNode())
                .andThen(new ShiftParserChainNode())
                .andThen(new AdditiveParserChainNode())
                .andThen(new MultiplicativeParserChainNode())
                .andThen(new PowerParserChainNode())
                .andThen(new UnaryParserChainNode())
                .andThen(new LiteralParserChainNode());
    }

    /**
     * Parses collection of tokens into expression tree.
     * @return parsed expression tree
     */
    public Expression parse() {
        return parserChain.parse(tokenHolder);
    }
}
