package den.vor.easy.object.value.operator;

import den.vor.easy.object.value.Value;

import java.util.function.BiFunction;

public class OperatorImpl<T, R> {
    private final Class<R> rClass;
    private final BiFunction<T, R, Value<?>> biFunction;

    public static <T, R> OperatorImpl<T, R> operator(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        return new OperatorImpl<>(rClass, biFunction);
    }

    public OperatorImpl(Class<R> rClass, BiFunction<T, R, Value<?>> biFunction) {
        this.rClass = rClass;
        this.biFunction = biFunction;
    }

    @SuppressWarnings("unchecked")
    public Value<?> apply(T t, Object r) {
        return biFunction.apply(t, (R) r);
    }

    public Class<R> getrClass() {
        return rClass;
    }
}
