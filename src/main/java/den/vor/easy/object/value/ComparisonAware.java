package den.vor.easy.object.value;

import den.vor.easy.object.value.impl.BooleanValue;

public interface ComparisonAware {

    default BooleanValue equalTo(Value<?> value) {
        return BooleanValue.of(equals(value));
    }

    default BooleanValue notEqualTo(Value<?> value) {
        return BooleanValue.of(!equals(value));
    }

    default BooleanValue gt(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default BooleanValue gte(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default BooleanValue lte(Value<?> value) {
        throw new UnsupportedOperationException();
    }

    default BooleanValue lt(Value<?> value) {
        throw new UnsupportedOperationException();
    }
}
