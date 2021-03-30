/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser;

/**
 * Utility class that stores constants required for expression evaluation.
 */
public class ExpressionConstants {

    public static final String PARENT_REF = "parent";
    public static final String THIS_REF = "this";

    private ExpressionConstants() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
