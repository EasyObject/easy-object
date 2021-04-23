/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.factory;

import com.den.vor.easy.object.factory.constraints.Bound;
import com.den.vor.easy.object.factory.constraints.SequenceConstraint;
import com.den.vor.easy.object.factory.constraints.SequenceConstraintsValues;
import com.den.vor.easy.object.factory.constraints.impl.GeConstraint;
import com.den.vor.easy.object.factory.constraints.impl.GtConstraint;
import com.den.vor.easy.object.factory.constraints.impl.LeConstraint;
import com.den.vor.easy.object.factory.constraints.impl.LtConstraint;
import com.den.vor.easy.object.parser.ExpressionEvaluator;
import com.den.vor.easy.object.ref.FieldRef;
import com.den.vor.easy.object.value.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for factories that generate a {@link Comparable} values.
 * @param <T> type of generated values
 * @param <R> corresponding wrapper type that extends {@link Value}
 */
public abstract class ComparableFactory<T extends Comparable<? super T>, R extends Value<T>> extends Factory<T, R> {

    private Bound<T> min;
    private Bound<T> max;
    private List<SequenceConstraint<T>> constraints = new ArrayList<>();

    /**
     * Creates a new factory instance with specified bounds.
     * @param min lower bound
     * @param max upper bound
     */
    protected ComparableFactory(T min, T max) {
        this.min = new Bound<>(min, true);
        this.max = new Bound<>(max, true);
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Expected min to be not bigger then max");
        }
    }

    /**
     * Generate a value between given bounds.
     * @param context generation context
     * @param min lower bound
     * @param max upper bound
     * @return value generated
     */
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

    /**
     * Add a {@code <} constraint in a form of expression.
     * See {@link LtConstraint}
     * @param expression expression to evaluate for each object to get a constraint value
     * @return ComparableFactory instance
     */
    public ComparableFactory<T, R> lt(String expression) {
        constraints.add(new LtConstraint<>(expression));
        return this;
    }

    /**
     * Add a {@code <=} constraint in a form of expression.
     * See {@link LeConstraint}
     * @param expression expression to evaluate for each object to get a constraint value
     * @return ComparableFactory instance
     */
    public ComparableFactory<T, R> le(String expression) {
        constraints.add(new LeConstraint<>(expression));
        return this;
    }

    /**
     * Add a {@code >} constraint in a form of expression.
     * See {@link GtConstraint}
     * @param expression expression to evaluate for each object to get a constraint value
     * @return ComparableFactory instance
     */
    public ComparableFactory<T, R> gt(String expression) {
        constraints.add(new GtConstraint<>(expression));
        return this;
    }

    /**
     * Add a {@code >=} constraint in a form of expression.
     * See {@link GeConstraint}
     * @param expression expression to evaluate for each object to get a constraint value
     * @return ComparableFactory instance
     */
    public ComparableFactory<T, R> ge(String expression) {
        constraints.add(new GeConstraint<>(expression));
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
