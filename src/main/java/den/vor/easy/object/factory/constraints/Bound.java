package den.vor.easy.object.factory.constraints;

public final class Bound<T> {

    private final T value;
    private final boolean inclusive;

    public Bound(T value, boolean inclusive) {
        this.value = value;
        this.inclusive = inclusive;
    }

    public T getValue() {
        return value;
    }

    public boolean isInclusive() {
        return inclusive;
    }
}