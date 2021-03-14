/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.facade;

import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

public class ValueFacade {

    public static StringValue of(String value) {
        return StringValue.of(value);
    }

    public static IntValue of(int value) {
        return IntValue.of(value);
    }

    public static DoubleValue of(double value) {
        return DoubleValue.of(value);
    }

    private ValueFacade() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
