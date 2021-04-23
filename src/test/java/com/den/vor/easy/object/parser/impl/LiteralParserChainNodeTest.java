/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.parser.impl;

import com.den.vor.easy.object.parser.ParserChainNode;
import com.den.vor.easy.object.parser.Token;
import com.den.vor.easy.object.parser.TokenHolder;
import com.den.vor.easy.object.parser.TokenType;
import com.den.vor.easy.object.parser.ast.*;
import com.den.vor.easy.object.parser.exception.impl.UnexpectedTokenException;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.*;
import com.den.vor.easy.object.bean.Period;
import den.vor.easy.object.parser.ast.*;
import den.vor.easy.object.value.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiteralParserChainNodeTest extends ParserChainNodeTestBase {

    @Mock
    private ParserChainNode root;
    @Mock
    private TokenHolder tokenHolder;

    private LiteralParserChainNode literalParserChainNode;

    @BeforeEach
    void setUp() {
        literalParserChainNode = new LiteralParserChainNode();
        literalParserChainNode.setRoot(root);
    }

    @Test
    void shouldThrowUnexpectedTokenExceptionWhenNoTokensMatch() {
        assertThrows(UnexpectedTokenException.class, () -> literalParserChainNode.parse(tokenHolder));
    }

    @Test
    void shouldReturnValueExpressionWithInt() {
        Token token = new Token(TokenType.INT_NUMBER, "1");

        when(tokenHolder.match(TokenType.INT_NUMBER)).thenReturn(true);
        when(tokenHolder.get(0)).thenReturn(token);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertValueExpression(expression, IntValue.of(1));
    }

    @Test
    void shouldReturnValueExpressionWithDouble() {
        Token token = new Token(TokenType.DOUBLE_NUMBER, "1.0");

        when(tokenHolder.match(TokenType.INT_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.DOUBLE_NUMBER)).thenReturn(true);
        when(tokenHolder.get(0)).thenReturn(token);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertValueExpression(expression, DoubleValue.of(1.0));
    }

    @Test
    void shouldReturnValueExpressionWithPeriod() {
        Token token = new Token(TokenType.PERIOD, "1y");

        when(tokenHolder.match(TokenType.INT_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.DOUBLE_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.PERIOD)).thenReturn(true);
        when(tokenHolder.get(0)).thenReturn(token);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertValueExpression(expression, PeriodValue.of(new Period().setYears(1)));
    }

    @Test
    void shouldReturnValueExpressionWithBoolean() {
        Token token = new Token(TokenType.BOOLEAN, "true");

        when(tokenHolder.match(TokenType.INT_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.DOUBLE_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.PERIOD)).thenReturn(false);
        when(tokenHolder.match(TokenType.BOOLEAN)).thenReturn(true);
        when(tokenHolder.get(0)).thenReturn(token);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertValueExpression(expression, BooleanValue.TRUE);
    }

    @Test
    void shouldReturnValueExpressionWithText() {
        Token token = new Token(TokenType.TEXT, "some text");

        when(tokenHolder.match(TokenType.INT_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.DOUBLE_NUMBER)).thenReturn(false);
        when(tokenHolder.match(TokenType.PERIOD)).thenReturn(false);
        when(tokenHolder.match(TokenType.BOOLEAN)).thenReturn(false);
        when(tokenHolder.match(TokenType.TEXT)).thenReturn(true);
        when(tokenHolder.get(0)).thenReturn(token);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertValueExpression(expression, StringValue.of("some text"));
    }

    @Test
    void shouldReturnVariableMapAccessExpressionWithTwoKeyHops(@Mock Expression thirdKey) {
        Token token1 = new Token(TokenType.WORD, "key1");
        Token token2 = new Token(TokenType.WORD, "key2");

        when(tokenHolder.lookMatch(TokenType.WORD)).thenReturn(true);
        when(tokenHolder.lookMatch(1, TokenType.LPAREN)).thenReturn(false);
        when(tokenHolder.lookMatch(TokenType.DOT)).thenReturn(true).thenReturn(false);
        when(tokenHolder.lookMatch(1, TokenType.WORD)).thenReturn(true);
        when(tokenHolder.lookMatch(TokenType.LBRACKET)).thenReturn(true).thenReturn(false);
        when(tokenHolder.lookMatch(2, TokenType.LPAREN)).thenReturn(false);
        when(tokenHolder.consume(TokenType.WORD)).thenReturn(token1).thenReturn(token2);

        when(root.parse(tokenHolder)).thenReturn(thirdKey);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertTrue(expression instanceof VariableMapAccessExpression, () -> "Expected LiteralParserChainNode to " +
                "return VariableMapAccessExpression, got - " + expression);
        VariableMapAccessExpression variableMapAccessExpression = (VariableMapAccessExpression) expression;

        List<Expression> keys = variableMapAccessExpression.getKeys();
        assertEquals(3, keys.size(), () -> "Expected VariableMapAccessExpression to have 2 args, " +
                "got - " + keys);

        // assert first key
        assertTrue(keys.get(0) instanceof ValueExpression);
        ValueExpression firstKey = (ValueExpression) keys.get(0);
        assertEquals(StringValue.of("key1"), firstKey.getValue());
        // assert second key
        assertTrue(keys.get(1) instanceof ValueExpression);
        ValueExpression secondKey = (ValueExpression) keys.get(1);
        assertEquals(StringValue.of("key2"), secondKey.getValue());
        // assert third key
        assertEquals(thirdKey, keys.get(2));

        verify(tokenHolder).lookMatch(TokenType.WORD);
        verify(tokenHolder).lookMatch(1, TokenType.LPAREN);
        verify(tokenHolder, times(4)).lookMatch(TokenType.DOT);
        verify(tokenHolder).lookMatch(1, TokenType.WORD);
        verify(tokenHolder, times(3)).lookMatch(TokenType.LBRACKET);
        verify(tokenHolder).lookMatch(2, TokenType.LPAREN);
        verify(tokenHolder, times(2)).consume(TokenType.WORD);
    }

    @Test
    void shouldReturnFunctionCallWithTwoArgs(@Mock Expression firstArg, @Mock Expression secondArg) {
        Token token = new Token(TokenType.WORD, "funcName");

        when(tokenHolder.lookMatch(TokenType.WORD)).thenReturn(true);
        when(tokenHolder.lookMatch(1, TokenType.LPAREN)).thenReturn(true);
        when(tokenHolder.lookMatch(TokenType.RPAREN)).thenReturn(false).thenReturn(false).thenReturn(true);
        when(tokenHolder.consume(TokenType.WORD)).thenReturn(token);
        when(root.parse(tokenHolder)).thenReturn(firstArg).thenReturn(secondArg);

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertTrue(expression instanceof FunctionInvocationExpression, () -> "Expected LiteralParserChainNode to " +
                "return FunctionInvocationExpression, got - " + expression);
        FunctionInvocationExpression functionInvocationExpression = (FunctionInvocationExpression) expression;

        // assert function key
        Expression key = functionInvocationExpression.getExpression();
        assertTrue(key instanceof VariableMapAccessExpression);
        List<Expression> keys = ((VariableMapAccessExpression) key).getKeys();
        assertEquals(1, keys.size(), () -> "Expected VariableMapAccessExpression to have keys with size 1, " +
                "got - " + keys);
        Expression onlyKey = keys.get(0);
        assertTrue(onlyKey instanceof ValueExpression);
        Value<?> value = ((ValueExpression) onlyKey).getValue();
        assertEquals(StringValue.of("funcName"), value);

        // assert args
        List<Expression> args = functionInvocationExpression.getArgs();
        assertEquals(2, args.size(), () -> "Expected VariableMapAccessExpression to have 2 args, " +
                "got - " + args);
        assertEquals(firstArg, args.get(0), () -> "Expected " + firstArg + " to be the first arg of " + args);
        assertEquals(secondArg, args.get(1), () -> "Expected " + secondArg + " to be the second arg of " + secondArg);

        verify(tokenHolder).lookMatch(TokenType.WORD);
        verify(tokenHolder).lookMatch(1, TokenType.LPAREN);
        verify(tokenHolder, times(3)).lookMatch(TokenType.RPAREN);
        verify(tokenHolder).consume(TokenType.WORD);
        verify(root, times(2)).parse(tokenHolder);
    }

    @Test
    void shouldReturnMethodCallWithTwoArgs(@Mock Expression firstArg, @Mock Expression secondArg) {
        List<Token> tokens = List.of(
                new Token(TokenType.INT_NUMBER, "1"),
                new Token(TokenType.DOT, "."),
                new Token(TokenType.WORD, "field1"),
                new Token(TokenType.LBRACKET, "["),
                new Token(TokenType.WORD, "field2"),
                new Token(TokenType.RBRACKET, "]"),
                new Token(TokenType.DOT, "."),
                new Token(TokenType.WORD, "method"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.INT_NUMBER, "2"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.INT_NUMBER, "3"),
                new Token(TokenType.RPAREN, "(")
        );
        TokenHolder tokenHolder = new TokenHolder(tokens);

        when(root.parse(tokenHolder)).thenAnswer(invocation -> {
            tokenHolder.consume(TokenType.WORD);
            return new ValueExpression(StringValue.of("field2"));
        }).thenAnswer(invocation -> {
            tokenHolder.consume(TokenType.INT_NUMBER);
            return new ValueExpression(IntValue.of(2));
        }).thenAnswer(invocation -> {
            tokenHolder.consume(TokenType.INT_NUMBER);
            return new ValueExpression(IntValue.of(3));
        });

        Expression expression = literalParserChainNode.parse(tokenHolder);

        assertTrue(expression instanceof MethodInvocationExpression, () -> "Expected LiteralParserChainNode to " +
                "return FunctionInvocationExpression, got - " + expression);
        MethodInvocationExpression methodInvocationExpression = (MethodInvocationExpression) expression;

        // assert method name
        Expression method = methodInvocationExpression.getMethod();
        assertTrue(method instanceof ValueExpression);
        assertEquals(StringValue.of("method"), ((ValueExpression) method).getValue());

        // assert method args
        List<Expression> args = methodInvocationExpression.getArgs();
        assertEquals(2, args.size());
        Expression arg1 = args.get(0);
        assertTrue(arg1 instanceof ValueExpression);
        assertEquals(IntValue.of(2), ((ValueExpression) arg1).getValue());
        Expression arg2 = args.get(1);
        assertTrue(arg2 instanceof ValueExpression);
        assertEquals(IntValue.of(3), ((ValueExpression) arg2).getValue());

        // assert underlying expression
        Expression expr = methodInvocationExpression.getExpression();
        assertTrue(expr instanceof MapAccessExpression);
        List<Expression> keys1 = ((MapAccessExpression) expr).getKeys();
        assertEquals(1, keys1.size());
        Expression v1 = keys1.get(0);
        assertTrue(v1 instanceof ValueExpression);
        assertEquals(StringValue.of("field2"), ((ValueExpression) v1).getValue());

        Expression value = ((MapAccessExpression) expr).getValue();
        assertTrue(value instanceof MapAccessExpression);
        List<Expression> keys2 = ((MapAccessExpression) value).getKeys();
        assertEquals(1, keys2.size());
        Expression v2 = keys2.get(0);
        assertTrue(v2 instanceof ValueExpression);
        assertEquals(StringValue.of("field1"), ((ValueExpression) v2).getValue());

        Expression variable = ((MapAccessExpression) value).getValue();
        assertTrue(variable instanceof ValueExpression);
        assertEquals(IntValue.of(1), ((ValueExpression) variable).getValue());
    }
}
