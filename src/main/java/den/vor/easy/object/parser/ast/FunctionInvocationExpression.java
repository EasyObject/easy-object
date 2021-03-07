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
import den.vor.easy.object.value.impl.FunctionalValue;
import den.vor.easy.object.value.impl.NullValue;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionInvocationExpression implements Expression {

    private Expression expression;
    private List<Expression> args;

    public FunctionInvocationExpression(Expression expression, List<Expression> args) {
        this.expression = expression;
        this.args = args;
    }

    @Override
    public Value<?> eval(Variables params) {
        Value<?> value = expression.eval(params);
        if (!(value instanceof FunctionalValue)) {
            throw new RuntimeException();
        }
        FunctionalValue<?> functionalValue = (FunctionalValue<?>) value;
        List<Value<?>> argValues = args.stream().map(a -> a.eval(params)).collect(Collectors.toList());
        return functionalValue.invoke(NullValue.NULL, argValues);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Expression> getArgs() {
        return args;
    }

    public Expression getExpression() {
        return expression;
    }
}
