package den.vor.easy.object.parser.exception.runtime;

public class InterpreterNumberFormatException extends InterpreterRuntimeException {

    private final String value;

    public InterpreterNumberFormatException(String value) {
        this.value = value;
    }

    @Override
    public String getUserMessage() {
        return "[" + value + "] cant be cast to number";
    }
}
