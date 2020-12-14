package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;

import java.util.Objects;

public class BinaryNotEqOperator extends BinaryOperator {

    @Override
    public Value<?> evaluate(Value<?> first, Value<?> second) {
        return BooleanValue.of(!Objects.equals(first.getValue(), second.getValue()));
    }
}
