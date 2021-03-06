package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ComparableValue;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.operator.impl.DateTimeOperations;

import java.time.LocalDateTime;

public class DateTimeValue extends ComparableValue<LocalDateTime> {

    private final LocalDateTime value;

    public static DateTimeValue of(LocalDateTime value) {
        return new DateTimeValue(value);
    }

    private DateTimeValue(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DateTimeValue{" + value + '}';
    }

    @Override
    public Value<?> plus(Value<?> value) {
        return DateTimeOperations.PLUS_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> minus(Value<?> value) {
        return DateTimeOperations.MINUS_OPERATOR.apply(getValue(), value);
    }
}
