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

/**
 * Basic class for all value wrappers.
 * @param <T> type of encapsulated object
 */
public abstract class Value<T> implements OperationAware, ComparisonAware {

    /**
     * Links to the parent value if any. Is required to traverse value tree upwards.
     */
    private CompoundValue<?> parent;

    /**
     * Get the encapsulated value.
     * @return
     */
    @JsonValue
    public abstract T getValue();

    /**
     * Get a field or nested value by a scalar key.
     * By default throws {@link UnsupportedOperationException} because mainly values don't have any fields.
     * @param key field or nested value key
     * @return value's field or nested value
     */
    public Value<?> get(ScalarValue<?> key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a method declared by the value class.
     * @param key method name.
     * @return method in a form of {@link FunctionalValue}
     */
    public FunctionalValue<T> getMethod(ScalarValue<?> key) {
        return getMethods().get(key.getValue().toString());
    }

    /**
     * Cast a value to the specified class.
     * Usage example:
     * {@code String str = value.as(String.class)}
     * @param kClass target class.
     * @param <K>
     * @return
     */
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

    /**
     * Method that returns method map. Is used by {@link Value#getMethod(ScalarValue)}.
     * Returns an empty map by default.
     * @return method map.
     */
    protected Map<String, FunctionalValue<T>> getMethods() {
        return Collections.emptyMap();
    }

    /**
     * Get encapsulated value type.
     * @return encapsulated value type
     */
    public Class<?> getType() {
        return getValue().getClass();
    }
}
