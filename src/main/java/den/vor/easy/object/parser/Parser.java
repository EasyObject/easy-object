package den.vor.easy.object.parser;

import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.parser.exception.ExpectedAnotherTokenException;
import den.vor.easy.object.parser.exception.UnexpectedTokenException;
import den.vor.easy.object.value.impl.BooleanValue;
import den.vor.easy.object.value.impl.DoubleValue;
import den.vor.easy.object.value.impl.IntValue;
import den.vor.easy.object.value.impl.StringValue;

import java.util.ArrayList;
import java.util.List;

import static den.vor.easy.object.parser.TokenType.*;

public final class Parser {

    private static final Token EOF_TOKEN = new Token(EOF, "");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public Expression parse() {
        return expression();
    }

    private Expression expression() {
        return ternary();
    }

    private Expression ternary() {
        Expression result = logicalOr();

        if (match(QUESTION_SIGN)) {
            Expression then = expression();
            consume(COLON);
            Expression elseExpr = expression();

            return new TernaryExpression(result, then, elseExpr);
        }

        return result;
    }

    private Expression logicalOr() {
        Expression result = logicalAnd();

        while (true) {
            if (match(BARBAR)) {
                result = new ConditionalExpression(result, logicalAnd(), ConditionalExpression.Operation.OR);
                continue;
            }
            break;
        }

        return result;
    }

    private Expression logicalAnd() {
        Expression result = equality();

        while (true) {
            if (match(AMPAMP)) {
                result = new ConditionalExpression(result, equality(), ConditionalExpression.Operation.AND);
                continue;
            }
            break;
        }

        return result;
    }

    private Expression equality() {
        Expression result = conditional();

        if (match(EQEQ)) {
            return new ConditionalExpression(result, conditional(), ConditionalExpression.Operation.EQUALS);
        }
        if (match(EXCLEQ)) {
            return new ConditionalExpression(result, conditional(), ConditionalExpression.Operation.NOT_EQUALS);
        }
        return result;
    }

    private Expression conditional() {
        Expression result = additive();

        while (true) {
            if (match(LT)) {
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operation.LT);
                continue;
            }
            if (match(LTEQ)) {
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operation.LTEQ);
                continue;
            }
            if (match(GT)) {
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operation.GT);
                continue;
            }
            if (match(GTEQ)) {
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operation.GTEQ);
                continue;
            }
            break;
        }

        return result;
    }

    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            if (match(PLUS)) {
                result = new BinaryExpression(result, multiplicative(), BinaryExpression.Operation.PLUS);
                continue;
            }
            if (match(MINUS)) {
                result = new BinaryExpression(result, multiplicative(), BinaryExpression.Operation.MINUS);
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while (true) {
            if (match(STAR)) {
                result = new BinaryExpression(result, unary(), BinaryExpression.Operation.MULTIPLY);
                continue;
            }
            if (match(POW)) {
                result = new BinaryExpression(result, unary(), BinaryExpression.Operation.POW);
                continue;
            }
            if (match(SLASH)) {
                result = new BinaryExpression(result, unary(), BinaryExpression.Operation.DIVIDE);
                continue;
            }
            if (match(PERCENT)) {
                result = new BinaryExpression(result, unary(), BinaryExpression.Operation.REMAINDER);
                continue;
            }
            break;
        }

        return result;
    }

    private Expression unary() {
        if (match(MINUS)) {
            return new UnaryExpression(primary(), UnaryExpression.Operation.MINUS);
        }
        if (match(NOT)) {
            return new UnaryExpression(primary(), UnaryExpression.Operation.NOT);
        }
        if (match(PLUS)) {
            return new UnaryExpression(primary(), UnaryExpression.Operation.PLUS);
        }
        return primary();
    }

    private Expression literal() {
        final Token current = get(0);

        Expression expression = null;
        if (match(INT_NUMBER)) {
            IntValue intValue = IntValue.of(Integer.parseInt(current.getText()));
            expression = new ValueExpression(intValue);
        } else if (match(DOUBLE_NUMBER)) {
            DoubleValue doubleValue = DoubleValue.of(Double.parseDouble(current.getText()));
            expression = new ValueExpression(doubleValue);
        } else if (match(BOOLEAN)) {
            BooleanValue booleanValue = BooleanValue.of(Boolean.parseBoolean(current.getText()));
            expression = new ValueExpression(booleanValue);
        } else if (match(TEXT)) {
            StringValue stringValue = StringValue.of(current.getText());
            expression = new ValueExpression(stringValue);
//        } else if (lookMatch(LBRACKET)) {
//            Expression array = array();
//            value = lookMatch(LBRACKET) ? element(array) : array;
//        } else if (lookMatch(LBRACE)) {
//            Expression mapOrSet = mapOrSet();
//            value = lookMatch(LBRACKET) ? element(mapOrSet) : mapOrSet;
        } else if (lookMatch(WORD)) {
            if (lookMatch(1, LPAREN)) {
                expression = functionCall();
            } else {
                expression = getVarAccessChain();
            }
        } else if (match(LPAREN)) {
            Expression result = expression();
            match(RPAREN);
            return result;
        } else {
            throw new UnexpectedTokenException(get(0));
        }

        return rec(expression);
    }

    private Expression functionCall() {
        String methodName = consume(WORD).getText();
        consume(LPAREN);
        List<Expression> arguments = new ArrayList<>();
        while (!lookMatch(RPAREN)) {
            if (!arguments.isEmpty()) {
                consume(COMMA);
            }
            arguments.add(expression());
        }
        List<Expression> keys = List.of(new ValueExpression(StringValue.of(methodName)));
        Expression functionName = new VariableMapAccessExpression(keys);
        return rec(new FunctionInvocationExpression(functionName, arguments));
    }

    private Expression getVarAccessChain() {
        List<Expression> keys = new ArrayList<>();
        String text = consume(WORD).getText();
        keys.add(new ValueExpression(StringValue.of(text)));
        while (true) {
            if (lookMatch(DOT) && lookMatch(1, WORD) && !lookMatch(2, LPAREN)) {
                consume(DOT);
                String key = consume(WORD).getText();
                keys.add(new ValueExpression(StringValue.of(key)));
            } else if (lookMatch(LBRACKET)) {
                consume(LBRACKET);
                keys.add(expression());
                consume(RBRACKET);
            } else {
                break;
            }
        }
        return new VariableMapAccessExpression(keys);
    }

    private Expression rec(Expression expression) {
        if (lookMatch(DOT) && lookMatch(1, WORD)) {
            if (lookMatch(2, LPAREN)) {
                consume(DOT);
                String methodName = consume(WORD).getText();
                consume(LPAREN);
                List<Expression> arguments = new ArrayList<>();
                while (!lookMatch(RPAREN)) {
                    if (!arguments.isEmpty()) {
                        consume(COMMA);
                    }
                    arguments.add(expression());
                }
                ValueExpression method = new ValueExpression(StringValue.of(methodName));
                return rec(new MethodInvocationExpression(expression, method, arguments));
            } else {
                consume(DOT);
                String key = consume(WORD).getText();
                ValueExpression e1 = new ValueExpression(StringValue.of(key));
                return rec(new MapAccessExpression(expression, List.of(e1)));
            }
        } else if (lookMatch(LBRACKET)) {
            consume(LBRACKET);
            MapAccessExpression mapAccessExpression = new MapAccessExpression(expression, List.of(expression()));
            consume(RBRACKET);
            return rec(mapAccessExpression);
        }
        return expression;
    }

    private Expression primary() {
        return literal();
    }
    
    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            throw new ExpectedAnotherTokenException(current, type);
        }
        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private boolean lookMatch(TokenType type) {
        return lookMatch(0, type);
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) {
            return EOF_TOKEN;
        }
        return tokens.get(position);
    }
}
