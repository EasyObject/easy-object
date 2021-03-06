package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.NumberValue;
import den.vor.easy.object.value.Value;

import java.math.BigInteger;

import static den.vor.easy.object.value.operator.impl.IntOperations.*;

public class BigIntValue extends NumberValue<BigInteger> {

    private final BigInteger value;

    public static BigIntValue of(BigInteger value) {
        return new BigIntValue(value);
    }

    public BigIntValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    @Override
    public StringValue toStringValue() {
        return StringValue.of(String.valueOf(value));
    }

    @Override
    public String toString() {
        return "BigIntValue{" + value + '}';
    }
}
