package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.impl.BooleanValue;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class BooleanOperations {

    public static final Operator<Boolean> AND_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a && b))
    );

    public static final Operator<Boolean> OR_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a || b))
    );

    public static final Operator<Boolean> XOR_OPERATOR = Operator.operator(
            operator(Boolean.class, (a, b) -> BooleanValue.of(a ^ b))
    );
}
