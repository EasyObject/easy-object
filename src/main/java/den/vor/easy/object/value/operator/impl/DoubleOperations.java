package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.StringValue;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class DoubleOperations {

    public static final Operator<Double> PLUS_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a + b.doubleValue())),
            operator(String.class, (a, b) -> StringValue.of(a + b))
    );

    public static final Operator<Double> MINUS_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a - b.doubleValue()))
    );

    public static final Operator<Double> MULTIPLY_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a * b.doubleValue()))
    );

    public static final Operator<Double> DIVIDE_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(a / b.doubleValue()))
    );

    public static final Operator<Double> POWER_OPERATOR = Operator.operator(
            operator(Number.class, (a, b) -> DoubleValue.of(Math.pow(a, b.doubleValue())))
    );
}
