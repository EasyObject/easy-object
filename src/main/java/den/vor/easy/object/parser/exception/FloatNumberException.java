package den.vor.easy.object.parser.exception;

public class FloatNumberException extends ParserException {

    @Override
    public String getUserMessage() {
        return "Invalid float number: didn't expect two dots";
    }
}
