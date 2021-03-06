package den.vor.easy.object.value.operator.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.operator.Operator;
import den.vor.easy.object.value.operator.util.TimeUtil;

import java.time.LocalDate;

import static den.vor.easy.object.value.operator.OperatorImpl.operator;

public class DateOperations {

    public static final Operator<LocalDate> PLUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::addPeriodToDate)
    );

    public static final Operator<LocalDate> MINUS_OPERATOR = Operator.operator(
            operator(Period.class, TimeUtil::subtractPeriodFromDate)
    );
}
