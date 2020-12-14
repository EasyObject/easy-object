package den.vor.easy.object.parser.exception;

import den.vor.easy.object.parser.Token;
import den.vor.easy.object.parser.TokenType;

public class ExpectedAnotherTokenException extends ParserException {

    private final Token token;
    private final TokenType expected;

    public ExpectedAnotherTokenException(Token token, TokenType expected) {
        this.token = token;
        this.expected = expected;
    }

    @Override
    public String getUserMessage() {
        return "Expected " + expected + " but got " + token.getType();
    }
}
