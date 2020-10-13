package den.vor.easy.object.factory;

public abstract class ComparableFactory<T> extends Factory<T> {

    private T min;
    private T max;
    private boolean inclusive;

    public abstract T getNext(T value);
    public abstract T getPrev(T value);
    public abstract T generate(GenerationContext context, T startInclusive, T endInclusive);

    @Override
    public Generator<T> getGenerator() {
        return context -> inclusive ? generate(context, min, max) : generate(context, getNext(min), getPrev(max));
    }

    public boolean isInclusive() {
        return inclusive;
    }

    public void setInclusive(boolean inclusive) {
        this.inclusive = inclusive;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }
}
