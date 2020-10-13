package den.vor.easy.object.value;

public abstract class Value<T> {

    public abstract T getValue();

    public Value<?> get(ScalarValue<?> index) {
        throw new UnsupportedOperationException();
    }
}
