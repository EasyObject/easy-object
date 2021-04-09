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
import den.vor.easy.object.value.ConstValue;
import den.vor.easy.object.value.OperationAware;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static den.vor.easy.object.value.impl.OperatorTestHelper.test;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StringValueTest {

    @Test
    void shouldReturnString_whenAddingAnythingToString(@Mock Object object) {
        when(object.toString()).thenReturn("some string");

        String first = "hello world ";
        String expected = "hello world some string";
        test(StringValue.of(first), new ConstValue<>(object))
                .withFunction(OperationAware::plus)
                .withFunctionSymbol("+")
                .verifyEquals(StringValue.of(expected));
    }


    @Test
    void shouldReturnPeriod_whenSubtractingPeriods() {
        Period first = new Period().setYears(3).setMonths(6).setDays(9).setHours(12).setMinutes(15)
                .setSeconds(18).setNanos(21);
        Period second = new Period().setYears(1).setMonths(2).setDays(3).setHours(4).setMinutes(5)
                .setSeconds(6).setNanos(7);
        Period expected = new Period().setYears(2).setMonths(4).setDays(6).setHours(8).setMinutes(10)
                .setSeconds(12).setNanos(14);

        test(PeriodValue.of(first), PeriodValue.of(second))
                .withFunction(OperationAware::minus)
                .withFunctionSymbol("-")
                .verifyEquals(PeriodValue.of(expected));
    }

    @Test
    void shouldReturnString_whenMultiplyingStringByInt() {
        String str = "abc";
        String expected = "abcabcabc";

        test(StringValue.of(str), IntValue.of(3))
                .withFunction(OperationAware::multiply)
                .withFunctionSymbol("*")
                .verifyEquals(StringValue.of(expected));
    }
}
