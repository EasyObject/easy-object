/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnaryExpressionTest {

    @Mock
    private Value<Object> value;
    @Mock
    private Value<Object> resultValue;
    @Mock
    private ValueExpression expression;
    @Mock
    private Variables variables;

    @Test
    public void shouldEvaluateChildExpressionsAndCallPlusMethodOnFirst() {
        doReturn(value).when(expression).eval(variables);

        UnaryExpression unaryExpression = new UnaryExpression(expression, UnaryExpression.Operation.MINUS);

        doReturn(resultValue).when(value).minus();

        Value<?> result = unaryExpression.eval(variables);

        verify(expression, only()).eval(variables);
        verify(value, only()).minus();

        assertEquals(resultValue, result);
    }

    @Test
    public void shouldCallVisitMethodOnVisitor_whenAccepted(@Mock ResultVisitor<Object> visitor,
                                                            @Mock UnaryExpression expression) {
        Object expected = new Object();
        when(visitor.visit(any(UnaryExpression.class))).thenReturn(expected);
        when(expression.accept(any())).thenCallRealMethod();

        Object result = expression.accept(visitor);

        verify(visitor, only()).visit(expression);
        verify(expression, only()).accept(visitor);

        assertEquals(expected, result);
    }
}
