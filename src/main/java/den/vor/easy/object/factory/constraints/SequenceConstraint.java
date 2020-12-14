package den.vor.easy.object.factory.constraints;


import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.value.impl.MapValue;

public abstract class SequenceConstraint<T extends Comparable<? super T>> {

    private final ExpressionEvaluator expressionEvaluator;

    protected SequenceConstraint(String constraint) {
        this.expressionEvaluator = new ExpressionEvaluator(constraint, MapValue.emptyMap());
    }

    public abstract SequenceConstraintsValues<T> apply(SequenceConstraintsValues<T> u, T value);

    public ExpressionEvaluator getExpressionEvaluator() {
        return expressionEvaluator;
    }
}
