/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalDateTime;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class DateTimeOperations {

    public static final Operator<LocalDateTime> PLUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::addPeriodToDateTime)
    );

    public static final Operator<LocalDateTime> MINUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::subtractPeriodFromDateTime)
    );

    private DateTimeOperations() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
