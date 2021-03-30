/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.ast;

import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.*;

import java.time.LocalDateTime;

/**
 * Class that provides constants for {@link Variables}.
 */
public class Constants {

    /**
     * Mathematical constant 'pi', approximately equal 3.1415.
     */
    public static final DoubleValue PI = DoubleValue.of(Math.PI);

    /**
     * Function that accepts a single argument. If it is double or string, parses to integer. Returns integer as is.
     * Throws {@link UnsupportedOperationException} for all other argument types.
     */
    public static final FunctionalValue<Integer> INT = new FunctionalValue<>((c, args) -> {
        if (args.size() != 1) {
            throw new RuntimeException();
        }
        Value<?> param = args.get(0);
        if (param instanceof IntValue) {
            return param;
        }
        if (param instanceof DoubleValue) {
            double value = ((DoubleValue) param).getValue();
            return IntValue.of((int) value);
        }
        if (param instanceof StringValue) {
            int value = Integer.parseInt(((StringValue) param).getValue());
            return IntValue.of(value);
        }
        throw new UnsupportedOperationException();
    }, true);

    /**
     * Function that does not accept any arguments and returns current date time.
     */
    public static final FunctionalValue<LocalDateTime> NOW = new FunctionalValue<>((c, args) -> {
        if (!args.isEmpty()) {
            throw new RuntimeException();
        }
        return DateTimeValue.of(LocalDateTime.now());
    }, true);

    private Constants() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}