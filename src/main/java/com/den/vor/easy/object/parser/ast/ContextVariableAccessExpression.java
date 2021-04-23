/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.ast;

import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.parser.visitors.ResultVisitor;
import com.den.vor.easy.object.value.ScalarValue;

import java.util.List;

/**
 * Expression that traverses object that is currently under construction to access it's field.
 */
public class ContextVariableAccessExpression implements Expression {

    /**
     * Number of parent links that should be followed before traversing tree down by {@code keyHops}.
     */
    private final int parentHops;
    /**
     * List of values that represent keys for traversing object's tree down.
     */
    private final List<ScalarValue<?>> keyHops;

    public ContextVariableAccessExpression(int parentHops, List<ScalarValue<?>> keyHops) {
        this.parentHops = parentHops;
        this.keyHops = keyHops;
    }

    /**
     * Traverses object's tree.
     * @param params variables to get context from.
     * @return object field
     */
    @Override
    public Value<?> eval(Variables params) {
        Value<?> value = params.getContext();
        for (int i = 0; i < parentHops; i++) {
            value = value.getParent();
        }
        for (ScalarValue<?> keyHop : keyHops) {
            value = value.get(keyHop);
        }
        return value;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public int getParentHops() {
        return parentHops;
    }

    public List<ScalarValue<?>> getKeyHops() {
        return keyHops;
    }
}
