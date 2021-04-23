/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.operator.impl;

import io.github.easyobject.core.value.impl.DateValue;
import io.github.easyobject.core.value.operator.BinaryOperator;
import io.github.easyobject.core.value.operator.BinaryOperatorImpl;
import io.github.easyobject.core.bean.Period;
import io.github.easyobject.core.value.operator.util.TimeUtil;

import java.time.LocalDate;

/**
 * Class that contains operator implementation for {@link DateValue}.
 */
public class DateOperations {

    /**
     * Binary addition is valid only when another operand is {@link Period}.
     * Returns {@link DateValue}.
     * See {@link TimeUtil#addPeriodToDate} for implementation details.
     */
    public static final BinaryOperator<LocalDate> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::addPeriodToDate)
    );

    /**
     * Binary subtraction is valid only when another operand is {@link Period}.
     * Returns {@link DateValue}.
     * See {@link TimeUtil#subtractPeriodFromDate} for implementation details.
     */
    public static final BinaryOperator<LocalDate> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::subtractPeriodFromDate)
    );

    private DateOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
