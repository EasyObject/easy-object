package den.vor.easy.object.parser.exception;

public class WrongTypeException extends ParserException {

    private final Class<?> expected;
    private final Class<?> actual;

    public WrongTypeException(Class<?> expected, Class<?> actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String getUserMessage() {
        return "Expected " + expected + ", got " + actual + " as an expression result";
    }

    @Override
    public String toString() {
        return getUserMessage();
    }
}
