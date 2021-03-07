/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.Value;

import static den.vor.easy.object.value.operator.impl.BooleanOperations.*;

public class BooleanValue extends ScalarValue<Boolean> {

    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public static BooleanValue of(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "value=" + value +
                '}';
    }

    @Override
    public Value<?> and(Value<?> value) {
        return AND_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> or(Value<?> value) {
        return OR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> xor(Value<?> value) {
        return XOR_OPERATOR.apply(getValue(), value);
    }

    @Override
    public Value<?> not() {
        return of(!value);
    }
}
