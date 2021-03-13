/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

import den.vor.easy.object.parser.ast.Expression;
import den.vor.easy.object.parser.impl.*;

public final class Parser {

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

    public Expression parse() {
        return parserChain.parse(tokenHolder);
    }
}
