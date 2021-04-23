/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.operator.impl;

import com.den.vor.easy.object.value.impl.DateTimeValue;
import com.den.vor.easy.object.value.operator.BinaryOperator;
import com.den.vor.easy.object.value.operator.BinaryOperatorImpl;
import com.den.vor.easy.object.bean.Period;
import com.den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalDateTime;

/**
 * Class that contains operator implementation for {@link DateTimeValue}.
 */
public class DateTimeOperations {

    /**
     * Binary addition is valid only when another operand is {@link Period}.
     * Returns {@link DateTimeValue}.
     * See {@link TimeUtil#addPeriodToDateTime} for implementation details.
     */
    public static final BinaryOperator<LocalDateTime> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::addPeriodToDateTime)
    );

    /**
     * Binary subtraction is valid only when another operand is {@link Period}.
     * Returns {@link DateTimeValue}.
     * See {@link TimeUtil#subtractPeriodFromDateTime} for implementation details.
     */
    public static final BinaryOperator<LocalDateTime> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::subtractPeriodFromDateTime)
    );

    private DateTimeOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
