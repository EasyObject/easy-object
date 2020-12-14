package den.vor.easy.object.parser.exception.runtime;

public class NotCallableException extends InterpreterRuntimeException {

    private final String name;

    public NotCallableException(String name) {
        this.name = name;
    }

    @Override
    public String getUserMessage() {
        return name + " is not callable";
    }
}
