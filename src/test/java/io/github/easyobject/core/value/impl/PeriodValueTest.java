/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.value.impl;

import io.github.easyobject.core.value.OperationAware;
import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.bean.Period;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PeriodValueTest {

    @Nested
    class SumTest {
        @Test
        void shouldReturnPeriod_whenAddingPeriods() {
            Period first = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                    .setSeconds(6).setNanos(7);
            Period second = new Period().setYears(2).setMonths(3).setDays(4).setHours(5).setMinutes(6)
                    .setSeconds(7).setNanos(8);
            Period expected = new Period().setYears(3).setMonths(5).setDays(7).setHours(9).setMinutes(11)
                    .setSeconds(13).setNanos(15);

            OperatorTestHelper.test(PeriodValue.of(first), PeriodValue.of(second))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(PeriodValue.of(expected));
        }

        @Test
        void shouldReturnTime_whenAddingTimeToPeriod() {
            LocalTime initial = LocalTime.parse("13:10:15", DateTimeFormatter.ISO_TIME);
            LocalTime expected = LocalTime.parse("10:15:10", DateTimeFormatter.ISO_TIME);
            Period period = new Period().setHours(21).setMinutes(4).setSeconds(55);
            OperatorTestHelper.test(PeriodValue.of(period), TimeValue.of(initial))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(TimeValue.of(expected));
        }

        @Test
        void shouldReturnDateTime_whenAddingDateTimeToPeriod() {
            LocalDateTime initial = LocalDateTime.parse("2020-10-08T13:10:15", DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime expected = LocalDateTime.parse("2021-11-17T10:15:10", DateTimeFormatter.ISO_DATE_TIME);
            Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1)
                    .setHours(21).setMinutes(4).setSeconds(55);
            OperatorTestHelper.test(PeriodValue.of(period), DateTimeValue.of(initial))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DateTimeValue.of(expected));
        }

        @Test
        void shouldReturnDate_whenAddingDateToPeriod() {
            LocalDate initial = LocalDate.parse("2020-10-08", DateTimeFormatter.ISO_DATE);
            LocalDate expected = LocalDate.parse("2021-11-16", DateTimeFormatter.ISO_DATE);
            Period period = new Period().setYears(1).setMonths(1).setWeeks(1).setDays(1);
            OperatorTestHelper.test(PeriodValue.of(period), DateValue.of(initial))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(DateValue.of(expected));
        }

        @Test
        void shouldReturnString_whenAddingStringToPeriod() {
            String str = "a";
            Period period = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                    .setSeconds(6).setNanos(7);
            String expected = "1y2m3d4h5M6s7na";
            OperatorTestHelper.test(PeriodValue.of(period), StringValue.of(str))
                    .withFunction(OperationAware::plus)
                    .withFunctionSymbol("+")
                    .verifyEquals(StringValue.of(expected));

        }

    }

    @Test
    void shouldReturnPeriod_whenSubtractingPeriods() {
        Period first = new Period().setYears(3).setMonths(6).setDays(9).setHours(12).setMinutes(15)
                .setSeconds(18).setNanos(21);
        Period second = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                .setSeconds(6).setNanos(7);
        Period expected = new Period().setYears(2).setMonths(4).setDays(6).setHours(8).setMinutes(10)
                .setSeconds(12).setNanos(14);

        OperatorTestHelper.test(PeriodValue.of(first), PeriodValue.of(second))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(PeriodValue.of(expected));
    }

    @Test
    void shouldReturnPeriod_whenMultiplyingPeriodByInt() {
        Period first = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                .setSeconds(6).setNanos(7);
        Period expected = new Period().setYears(3).setMonths(6).setDays(9).setHours(12).setMinutes(15)
                .setSeconds(18).setNanos(21);

        OperatorTestHelper.test(PeriodValue.of(first), IntValue.of(3))
                .withFunction(OperationAware::multiply)
                .withFunctionSymbol("*")
                .verifyEquals(PeriodValue.of(expected));
    }

    @Test
    void shouldReturnPeriod_whenUnaryMinusCalled() {
        Period first = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                .setSeconds(6).setNanos(7);
        Period expected = new Period().setYears(-1).setMonths(-2).setDays(-3).setHours(-4).setMinutes(-5)
                .setSeconds(-6).setNanos(-7);

        Value<?> result = PeriodValue.of(first).minus();
        assertEquals(expected, result.getValue(), () -> "Expected minus " + first + " to return " + expected +
                ", got " + result.getValue());
    }
}
