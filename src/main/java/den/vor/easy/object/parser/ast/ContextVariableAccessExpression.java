package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import java.util.List;

public class ContextVariableAccessExpression implements Expression {

    private final int parentHops;
    private final List<ScalarValue<?>> keyHops;

    public ContextVariableAccessExpression(int parentHops, List<ScalarValue<?>> keyHops) {
        this.parentHops = parentHops;
        this.keyHops = keyHops;
    }

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
