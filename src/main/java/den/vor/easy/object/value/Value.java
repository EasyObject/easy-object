/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value;

import com.fasterxml.jackson.annotation.JsonValue;
import den.vor.easy.object.value.impl.FunctionalValue;

import java.util.Collections;
import java.util.Map;

public abstract class Value<T> implements OperationAware, ComparisonAware {

    private CompoundValue<?> parent;

    @JsonValue
    public abstract T getValue();

    public Value<?> get(ScalarValue<?> key) {
        throw new UnsupportedOperationException();
    }

    public FunctionalValue<T> getMethod(ScalarValue<?> key) {
        return getMethods().get(key.getValue().toString());
    }

    @SuppressWarnings("unchecked")
    public <K> K as(Class<K> kClass) {
        return (K) getValue();
    }

    public CompoundValue<?> getParent() {
        return parent;
    }

    public void setParent(CompoundValue<?> parent) {
        this.parent = parent;
    }

    protected Map<String, FunctionalValue<T>> getMethods() {
        return Collections.emptyMap();
    }

    public Class<?> getType() {
        return getValue().getClass();
    }
}
