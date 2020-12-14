package den.vor.easy.object.value;

public class ConstValue<T> extends Value<T> {

    private final T value;

    public ConstValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
