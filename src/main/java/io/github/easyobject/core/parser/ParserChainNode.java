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

/**
 * Parent class for concrete parsers in {@link ParserChain}.
 */
public abstract class ParserChainNode {

    /**
     * Next parser in chain. Current parser may delegate invocation to it.
     */
    private ParserChainNode next;
    /**
     * Root parser, head of the chain.
     */
    private ParserChainNode root;

    /**
     * Parse a collection of tokens into an expression tree.
     * @param tokenHolder holder of tokens
     * @return parsed expression tree
     */
    public abstract Expression parse(TokenHolder tokenHolder);

    /**
     * Delegates a call to the next parser in the chain.
     * @throws NullPointerException if current parser is the last.
     * @param tokenHolder holder of tokens
     * @return parsed expression tree
     */
    protected Expression parseNext(TokenHolder tokenHolder) {
        return getNext().parse(tokenHolder);
    }

    /**
     * Gets a next parser in the chain.
     * @throws NullPointerException if current parser is the last.
     * @return next parser in the chain
     */
    protected ParserChainNode getNext() {
        if (next == null) {
            throw new NullPointerException("Last node in chain tried to get next");
        }
        return next;
    }

    /**
     * Gets a root chain parser. If it's {@code null}, current parser is considered root.
     * @return root parser
     */
    protected ParserChainNode getRoot() {
        if (root == null) {
            root = this;
        }
        return root;
    }

    public ParserChainNode setNext(ParserChainNode next) {
        this.next = next;
        return this;
    }

    public ParserChainNode setRoot(ParserChainNode root) {
        this.root = root;
        return this;
    }
}
