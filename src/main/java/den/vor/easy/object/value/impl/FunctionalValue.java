package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.Value;

import java.util.List;
import java.util.function.BiFunction;

public class FunctionalValue<T> extends Value<Object> {

    private final BiFunction<Value<T>, List<Value<?>>, Value<?>> function;
    private boolean idempotent;

    public FunctionalValue(BiFunction<Value<T>, List<Value<?>>, Value<?>> function) {
        this.function = function;
    }

    public FunctionalValue(BiFunction<Value<T>, List<Value<?>>, Value<?>> function, boolean idempotent) {
        this.function = function;
        this.idempotent = idempotent;
    }

    @SuppressWarnings("unchecked")
    public Value<?> invoke(Value<?> context, List<Value<?>> args) {
        return function.apply((Value<T>) context, args);
    }

    @Override
    public Object getValue() {
        return null;
    }

    public boolean isIdempotent() {
        return idempotent;
    }
}
