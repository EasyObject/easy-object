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
import den.vor.easy.object.value.Value;

/**
 * Expression that returns a constant value as a result of evaluation.
 */
public class ValueExpression implements Expression {

    private final Value<?> value;

    public ValueExpression(Value<?> value) {
        this.value = value;
    }

    @Override
    public Value<?> eval(Variables variables) {
        return value;
    }

    @Override
    public String toString() {
        return "ValueExpression{" + value + '}';
    }

    public Value<?> getValue() {
        return value;
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
