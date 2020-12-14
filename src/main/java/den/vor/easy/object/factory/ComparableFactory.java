package den.vor.easy.object.factory;

import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.factory.constraints.SequenceConstraint;
import den.vor.easy.object.factory.constraints.SequenceConstraintsValues;
import den.vor.easy.object.factory.constraints.impl.LtConstraint;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.ref.FieldRef;
import den.vor.easy.object.value.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ComparableFactory<T extends Comparable<T>, R extends Value<T>> extends Factory<T, R> {

    private Bound<T> min;
    private Bound<T> max;
    private List<SequenceConstraint<T>> constraints = new ArrayList<>();

    public ComparableFactory(T min, T max) {
        this.min = new Bound<>(min, true);
        this.max = new Bound<>(max, true);
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Expected min to be not bigger then max");
        }
    }

    protected abstract R getBetween(GenerationContext context, Bound<T> min, Bound<T> max);

    @Override
    public Generator<R> getGenerator() {
        if (constraints.isEmpty()) {
            return context -> getBetween(context, min, max);
        }
        return context -> {
            SequenceConstraintsValues<T> values = new SequenceConstraintsValues<>(min, max);
            for (SequenceConstraint<T> constraint : constraints) {
                Value<?> constraintValue = constraint.getExpressionEvaluator().evaluate(context.getContext());
                T value = cast(constraintValue);
                values = constraint.apply(values, value);
            }
            return getBetween(context, values.getMin(), values.getMax());
        };
    }

    protected abstract T cast(Value<?> value);

    public ComparableFactory<T, R> lt(String path) {
        constraints.add(new LtConstraint<>(path));
        return this;
    }

    @Override
    public List<FieldRef> getDependencies() {
        return constraints.stream()
                .map(SequenceConstraint::getExpressionEvaluator)
                .map(ExpressionEvaluator::getDependencies)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
}
