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

public class ParserChain {

    private ParserChainNode head;
    private ParserChainNode tail;

    public ParserChain() {
        this.head = new HeadParserChainNode();
        this.tail = head;
    }

    public Expression parse(TokenHolder tokenHolder) {
        return head.parse(tokenHolder);
    }

    public ParserChain andThen(ParserChainNode node) {
        tail.setNext(node);
        tail = node;
        node.setRoot(head);
        return this;
    }

    protected static class HeadParserChainNode extends ParserChainNode {

        @Override
        public Expression parse(TokenHolder tokenHolder) {
            return getNext().parse(tokenHolder);
        }
    }
}
