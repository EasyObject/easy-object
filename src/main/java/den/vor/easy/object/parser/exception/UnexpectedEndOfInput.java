package den.vor.easy.object.parser.exception;

public class UnexpectedEndOfInput extends ParserException {

    private char expectedChar;

    public UnexpectedEndOfInput(char expectedChar) {
        this.expectedChar = expectedChar;
    }

    @Override
    public String getUserMessage() {
        return "Expected " + expectedChar + ", got end of input";
    }
}
