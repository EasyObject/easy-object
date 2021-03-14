/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.integration.factory;

import org.junit.jupiter.api.Test;

import static den.vor.easy.object.facade.EasyObject.isInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntFactoryTest {

    @Test
    void shouldReturnNumbersBetweenMinAndMax() {
        FactoryTestHelper
                .of(isInt(10, 20))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lte higher bound");
                }).test();
    }

    @Test
    void shouldReturnNumbersBetweenMinAndHighConstraintExcluding_whenLtConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).lt("18"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i < 18, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    void shouldReturnNumbersBetweenMinAndHighConstraintIncluding_whenLeConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).le("18"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 18, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    void shouldReturnNumbersBetweenLowConstraintIncludingAndMax_whenGeConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).ge("12"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 12, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    void shouldReturnNumbersBetweenLowConstraintExcludingAndMax_whenGtConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).gt("12"))
                .withAssertionRunner(i -> {
                    assertTrue(i > 12, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lt high constraint bound");
                }).test();
    }
}
