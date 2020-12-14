package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;

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
