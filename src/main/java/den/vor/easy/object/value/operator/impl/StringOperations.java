package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.impl.StringValue;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class StringOperations {

    public static final Operator<String> PLUS_OPERATOR = Operator.operator(
            operator(Object.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<String> MULTIPLY_OPERATOR = Operator.operator(
            operator(Integer.class, (a, b) -> StringValue.of(a.repeat(b)))
    );
}
