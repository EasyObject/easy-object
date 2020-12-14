package den.vor.easy.object.parser.exception;

import den.vor.easy.object.parser.Token;

public class UnexpectedTokenException extends ParserException {

    private final Token token;

    public UnexpectedTokenException(Token token) {
        this.token = token;
    }

    @Override
    public String getUserMessage() {
        return "Didn't expect token " + token.getType() + " (" + token.getText() + ")";
    }
}
