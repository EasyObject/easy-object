/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.ast;


import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapAccessExpression implements Expression {

    private final List<Expression> keys;
    private final Expression value;

    public MapAccessExpression(Expression value, List<Expression> keys) {
        this.keys = keys;
        this.value = value;
    }

    @Override
    public Value<?> eval(Variables variables) {
        Value<?> value = this.value.eval(variables);
        for (Expression key : keys) {
            Value<?> keyValue = key.eval(variables);
            if (keyValue instanceof ScalarValue) {
                ScalarValue<?> scalarValue = (ScalarValue<?>) keyValue;
                value = value.get(scalarValue);
            } else {
                throw new RuntimeException();
            }
        }
        return value;
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
