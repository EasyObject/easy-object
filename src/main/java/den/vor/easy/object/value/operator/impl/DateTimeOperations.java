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
}
