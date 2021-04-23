/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.ast;


import io.github.easyobject.core.parser.exception.impl.ScalarValueExpectedException;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.parser.visitors.ResultVisitor;
import io.github.easyobject.core.value.ScalarValue;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Expression that traverses nested maps by the specified keys.
 */
public class MapAccessExpression implements Expression {

    /**
     * Expressions to use to traverse map.
     */
    private final List<Expression> keys;
    /**
     * Expression that is used as a starting point for traversal.
     */
    private final Expression value;

    public MapAccessExpression(Expression value, List<Expression> keys) {
        this.keys = keys;
        this.value = value;
    }

    /**
     * Evaluated {@code expression}, then evaluates {@code keys} one by one and traverses {@code expression} using them.
     * Requires all keys to be {@link ScalarValue}.
     *
     * @param variables variables to use during the evaluation
     * @return variable access result
     * @throws ScalarValueExpectedException if any key is not a {@linkplain ScalarValue}
     */
    @Override
    public Value<?> eval(Variables variables) {
        Value<?> result = value.eval(variables);
        for (Expression key : keys) {
            Value<?> keyValue = key.eval(variables);
            if (!(keyValue instanceof ScalarValue)) {
                throw new ScalarValueExpectedException(keyValue);
            }
            ScalarValue<?> scalarValue = (ScalarValue<?>) keyValue;
            result = result.get(scalarValue);
        }
        return result;
    }

    public Expression getValue() {
        return value;
    }

    public List<Expression> getKeys() {
        return keys;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return value + "[" + keys.stream().map(Objects::toString).collect(Collectors.joining()) + ']';
    }
}
