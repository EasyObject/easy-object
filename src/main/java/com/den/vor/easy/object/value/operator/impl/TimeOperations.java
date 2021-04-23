/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.value.operator.impl;

import com.den.vor.easy.object.value.impl.TimeValue;
import com.den.vor.easy.object.value.operator.BinaryOperator;
import com.den.vor.easy.object.value.operator.BinaryOperatorImpl;
import com.den.vor.easy.object.bean.Period;
import com.den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalTime;

/**
 * Class that contains operator implementation for {@link TimeValue}.
 */
public class TimeOperations {

    /**
     * Binary addition is valid only when another operand is {@link Period}.
     * Returns {@link TimeValue}.
     * See {@link TimeUtil#addPeriodToTime} for implementation details.
     */
    public static final BinaryOperator<LocalTime> PLUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::addPeriodToTime)
    );

    /**
     * Binary subtraction is valid only when another operand is {@link Period}.
     * Returns {@link TimeValue}.
     * See {@link TimeUtil#subtractPeriodFromTime} for implementation details.
     */
    public static final BinaryOperator<LocalTime> MINUS_OPERATOR = BinaryOperator.operator(
            BinaryOperatorImpl.operator(Period.class, TimeUtil::subtractPeriodFromTime)
    );

    private TimeOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
