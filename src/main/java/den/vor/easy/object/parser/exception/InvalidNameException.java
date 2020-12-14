package den.vor.easy.object.parser.exception;

public class InvalidNameException extends ParserException {

    private final String name;

    public InvalidNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUserMessage() {
        return "Invalid name [" + name + "]";
    }
}
