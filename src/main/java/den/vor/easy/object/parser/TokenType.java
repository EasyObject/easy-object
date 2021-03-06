package den.vor.easy.object.parser;

public enum TokenType {
    QUESTION_SIGN,
    COLON,

    PERIOD,
    DATE,

    DOUBLE_NUMBER,
    INT_NUMBER,
    BOOLEAN,
    WORD,
    TEXT,
    IN,
    DOT,
    IS,
    NONE,
    PERCENT,
    POW,

    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQ,
    EQEQ,
    EXCL,
    EXCLEQ,
    LT,
    LTEQ,
    GT,
    GTEQ,
    NOT,

    BAR,
    BARBAR,
    AMP,
    AMPAMP,
    CARET,

    LPAREN, // (
    RPAREN, // )
    LBRACKET, // [
    RBRACKET, // ]
    LBRACE, // {
    RBRACE, // }
    COMMA, // ,
    ARG, // ${

    LSHIFT, // <<
    RSHIFT, // >>

    EOF
}
