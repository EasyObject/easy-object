package den.vor.easy.object.integration.factory;

import org.junit.jupiter.api.Test;

import static den.vor.easy.object.facade.EasyObject.isInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntFactoryTest {

    @Test
    public void shouldReturnNumbersBetweenMinAndMax() {
        FactoryTestHelper
                .of(isInt(10, 20))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lte higher bound");
                }).test();
    }

    @Test
    public void shouldReturnNumbersBetweenMinAndHighConstraintExcluding_whenLtConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).lt("18"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i < 18, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    public void shouldReturnNumbersBetweenMinAndHighConstraintIncluding_whenLeConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).le("18"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 10, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 18, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    public void shouldReturnNumbersBetweenLowConstraintIncludingAndMax_whenGeConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).ge("12"))
                .withAssertionRunner(i -> {
                    assertTrue(i >= 12, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lt high constraint bound");
                }).test();
    }

    @Test
    public void shouldReturnNumbersBetweenLowConstraintExcludingAndMax_whenGtConstraintLessThatMax() {
        FactoryTestHelper
                .of(isInt(10, 20).gt("12"))
                .withAssertionRunner(i -> {
                    assertTrue(i > 12, "Expected generated value to be gte lower bound");
                    assertTrue(i <= 20, "Expected generated value to be lt high constraint bound");
                }).test();
    }


}