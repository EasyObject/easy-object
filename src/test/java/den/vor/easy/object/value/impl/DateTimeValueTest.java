package den.vor.easy.object.value.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.OperationAware;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;

public class DateTimeValueTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Test
    public void shouldReturnDateTime_whenAddingPeriodToDateTime() {
        LocalDateTime initial = LocalDateTime.parse("2020-10-08T13:10:15", FORMATTER);
        LocalDateTime expected = LocalDateTime.parse("2021-11-17T10:15:10", FORMATTER);
        Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1)
                .setHours(21).setMinutes(4).setSeconds(55);
        test(DateTimeValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::plus)
                .withFunctionSymbol("+")
                .verifyEquals(DateTimeValue.of(expected));
    }

    @Test
    public void shouldReturnTime_whenSubtractingPeriodFromDate() {
        LocalDateTime initial = LocalDateTime.parse("2021-11-17T10:15:10", FORMATTER);
        LocalDateTime expected = LocalDateTime.parse("2020-10-08T13:10:15", FORMATTER);
        Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1)
                .setHours(21).setMinutes(4).setSeconds(55);
        test(DateTimeValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(DateTimeValue.of(expected));
    }

}
