package den.vor.easy.object.parser.ast.operator;

import den.vor.easy.object.value.Value;

public abstract class UnaryOperator extends Operator {

    public abstract Value<?> evaluate(Value<?> value);
}
