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
class BinaryExpressionTest {

    @Mock
    private Value<Object> firstValue;
    @Mock
    private Value<Object> secondValue;
    @Mock
    private Value<Object> resultValue;
    @Mock
    private ValueExpression firstExpression;
    @Mock
    private ValueExpression secondExpression;
    @Mock
    private Variables variables;

    @Test
    void shouldEvaluateChildExpressionsAndCallPlusMethodOnFirst() {
        doReturn(firstValue).when(firstExpression).eval(variables);
        doReturn(secondValue).when(secondExpression).eval(variables);

        BinaryExpression binaryExpression = new BinaryExpression(firstExpression, secondExpression,
                BinaryExpression.Operation.PLUS);

        doReturn(resultValue).when(firstValue).plus(secondValue);

        Value<?> result = binaryExpression.eval(variables);

        verify(firstExpression, only()).eval(variables);
        verify(secondExpression, only()).eval(variables);
        verify(firstValue, only()).plus(secondValue);
        verifyNoInteractions(secondValue);

        assertEquals(resultValue, result);
    }

    @Test
    void shouldCallVisitMethodOnVisitor_whenAccepted(@Mock ResultVisitor<Object> visitor,
                                                            @Mock BinaryExpression expression) {
        Object expected = new Object();
        when(visitor.visit(any(BinaryExpression.class))).thenReturn(expected);
        when(expression.accept(any())).thenCallRealMethod();

        Object result = expression.accept(visitor);

        verify(visitor, only()).visit(expression);
        verify(expression, only()).accept(visitor);

        assertEquals(expected, result);
    }
}
