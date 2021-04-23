/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.bean.Period;
import io.github.easyobject.core.value.OperationAware;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class DateValueTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    @Test
    void shouldReturnDate_whenAddingPeriodToDate() {
        LocalDate initial = LocalDate.parse("2020-10-08", FORMATTER);
        LocalDate expected = LocalDate.parse("2021-11-16", FORMATTER);
        Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1);
        OperatorTestHelper.test(DateValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::plus)
                .withFunctionSymbol("+")
                .verifyEquals(DateValue.of(expected));
    }

    @Test
    void shouldReturnDate_whenAddingPeriodWithTimePartToDate() {
        LocalDate initial = LocalDate.parse("2020-10-08", FORMATTER);
        Period period = new Period().setHours(23).setMinutes(59).setSeconds(59).setNanos(999999);
        OperatorTestHelper.test(DateValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::plus)
                .withFunctionSymbol("+")
                .verifyEquals(DateValue.of(initial));
    }


    @Test
    void shouldReturnDate_whenSubtractingPeriodFromDate() {
        LocalDate initial = LocalDate.parse("2021-11-16", FORMATTER);
        LocalDate expected = LocalDate.parse("2020-10-08", FORMATTER);
        Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1);
        OperatorTestHelper.test(DateValue.of(initial), PeriodValue.of(period))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(DateValue.of(expected));
    }

}
