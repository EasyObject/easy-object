package den.vor.easy.object.facade;

import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

public class ValueFacade {

    public static StringValue of(String value) {
        return StringValue.of(value);
    }

    public static IntValue of(int value) {
        return IntValue.of(value);
    }

    public static DoubleValue of(double value) {
        return DoubleValue.of(value);
    }
}
