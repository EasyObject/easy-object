/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.value.impl;

import den.vor.easy.object.bean.Period;
import den.vor.easy.object.value.OperationAware;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;

public class TimeValueTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_TIME;

    @Test
    public void shouldReturnTime_whenAddingPeriodToTime() {
        LocalTime initial = LocalTime.parse("13:10:15", FORMATTER);
        LocalTime expected = LocalTime.parse("10:15:10", FORMATTER);
        Period period = new Period().setHours(21).setMinutes(4).setSeconds(55);
        test(TimeValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::plus)
                .withFunctionSymbol("+")
                .verifyEquals(TimeValue.of(expected));
    }

    @Test
    public void shouldReturnTime_whenSubtractingPeriodFromDate() {
        LocalTime initial = LocalTime.parse("10:15:10", FORMATTER);
        LocalTime expected = LocalTime.parse("13:10:15", FORMATTER);
        Period period = new Period().setHours(21).setMinutes(4).setSeconds(55);
        test(TimeValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(TimeValue.of(expected));
    }

}
