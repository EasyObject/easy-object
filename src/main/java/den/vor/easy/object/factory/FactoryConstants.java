/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory;

import den.vor.easy.object.value.impl.StringValue;

/**
 * Class that contains constants required for {@link Factory} and it's child classes.
 */
public class FactoryConstants {

    public static final StringValue COUNT_STRING_VALUE = StringValue.of("count");

    private FactoryConstants() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
