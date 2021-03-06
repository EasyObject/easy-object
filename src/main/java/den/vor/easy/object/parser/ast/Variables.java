package den.vor.easy.object.parser.ast;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Variables {

    private static final Map<StringValue, Value<?>> CONST_VALUES;

    static {
        Map<StringValue, Value<?>> consts = new HashMap<>();
        consts.put(StringValue.of("pi"), DoubleValue.of(Math.PI));
        consts.put(StringValue.of("int"), new FunctionalValue<>((context, args) -> {
            if (args.size() != 1) {
                throw new RuntimeException();
            }
            Value<?> param = args.get(0);
            if (param instanceof IntValue) {
                return param;
            }
            if (param instanceof DoubleValue) {
                double value = ((DoubleValue) param).getValue();
                return IntValue.of((int) value);
            }
            if (param instanceof StringValue) {
                int value = Integer.parseInt(((StringValue)param).getValue());
                return IntValue.of(value);
            }
            throw new UnsupportedOperationException();
        }, true));
        consts.put(StringValue.of("now"), new FunctionalValue<>((context, args) -> {
            if (args.size() != 0) {
                throw new RuntimeException();
            }
            return DateTimeValue.of(LocalDateTime.now());
        }, true));

        CONST_VALUES = Map.copyOf(consts);
    }

    private final MapValue globalVariables;
    private final Value<?> context;

    public Variables(MapValue globalVariables, Value<?> context) {
        this.globalVariables = globalVariables;
        this.context = context;
    }

    public Value<?> getVariable(StringValue key) {
        Value<?> value = getNullableConst(key);
        if (value == null) {
            value = context.get(key);
        }
        if (value == null) {
            value = NullValue.NULL;
        }
        return value;
    }

    public Value<?> getNullableConst(StringValue key) {
        Value<?> value = CONST_VALUES.get(key);
        if (value == null) {
            value = globalVariables.get(key);
        }
        return value;
    }

    public Value<?> getContext() {
        return context;
    }
}
