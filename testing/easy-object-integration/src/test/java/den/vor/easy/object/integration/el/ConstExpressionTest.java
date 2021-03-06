package den.vor.easy.object.integration.el;

import org.junit.jupiter.api.Test;

import static den.vor.easy.object.integration.el.TestEvaluator.evaluate;

public class ConstExpressionTest {

    @Test
    public void shouldReturnInt_whenLiteralContainsOnlyDigits() {
        evaluate("2").assertEquals(2);
    }

    @Test
    public void shouldReturnDouble_whenLiteralContainsDigitsAndOneDot() {
        evaluate("2.3").assertEquals(2.3);
    }

    @Test
    public void shouldReturnString_whenLiteralStartsAndEndsWithQuote() {
        evaluate("'someText'").assertEquals("someText");
    }

    @Test
    public void shouldReturnBooleanTrue_whenLiteralIsTrue() {
        evaluate("true").assertEquals(true);
    }

    @Test
    public void shouldReturnBooleanFalse_whenLiteralIsFalse() {
        evaluate("false").assertEquals(false);
    }
}
