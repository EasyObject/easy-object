/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser;

import com.den.vor.easy.object.parser.ast.Expression;

/**
 * Represents a chain of token parsers.
 */
public class ParserChain {

    /**
     * Head of a parser chain. Entrypoint for parsing process.
     */
    private ParserChainNode head;
    /**
     * Tail of a parser chain. Is used to append more parsers.
     */
    private ParserChainNode tail;

    public ParserChain() {
        this.head = new HeadParserChainNode();
        this.tail = head;
    }

    /**
     * Parses a provided collection of tokens into {@linkplain Expression} tree.
     * @param tokenHolder holder to take tokens from
     * @return parsed expression tree
     */
    public Expression parse(TokenHolder tokenHolder) {
        return head.parse(tokenHolder);
    }

    /**
     * Appends a new parser to the tail of a chain.
     * @param node node that should be appended
     * @return parser chain instance
     */
    public ParserChain andThen(ParserChainNode node) {
        tail.setNext(node);
        tail = node;
        node.setRoot(head);
        return this;
    }

    /**
     * Stub parser chain node implementation that always delegates parsing invocations to the next parser in the chain.
     */
    protected static class HeadParserChainNode extends ParserChainNode {

        @Override
        public Expression parse(TokenHolder tokenHolder) {
            return getNext().parse(tokenHolder);
        }
    }
}
