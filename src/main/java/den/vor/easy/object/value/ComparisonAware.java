/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
