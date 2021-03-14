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

public abstract class ParserChainNode {

    private ParserChainNode next;
    private ParserChainNode root;

    public abstract Expression parse(TokenHolder tokenHolder);

    protected Expression parseNext(TokenHolder tokenHolder) {
        return next.parse(tokenHolder);
    }

    protected ParserChainNode getNext() {
        if (next == null) {
            throw new NullPointerException("Last node in chain tried to get next");
        }
        return next;
    }

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
