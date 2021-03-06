package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalTime;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class TimeOperations {

    public static final Operator<LocalTime> PLUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::addPeriodToTime)
    );

    public static final Operator<LocalTime> MINUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::subtractPeriodFromTime)
    );
}
