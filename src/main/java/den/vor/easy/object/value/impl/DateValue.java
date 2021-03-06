package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ComparableValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.operator.impl.DateOperations;

import java.time.LocalDate;

public class DateValue extends ComparableValue<LocalDate> {

    private final LocalDate value;

    public static DateValue of(LocalDate value) {
        return new DateValue(value);
    }

    private DateValue(LocalDate value) {
        this.value = value;
    }

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DateValue{" + value + '}';
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return DateOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return DateOperations.MINUS_OPERATOR.apply(getValue(), value);
    }
}
