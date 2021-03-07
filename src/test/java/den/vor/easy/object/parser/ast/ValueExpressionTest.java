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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class ValueExpressionTest {

    @Mock
    private Value<?> value;

    @Test
    public void shouldReturnPassedValue_whenEvalCalled() {
        ValueExpression valueExpression = new ValueExpression(value);

        Value<?> result = valueExpression.getValue();

        assertSame(value, result, () -> "Expected ValueExpression to return same object as passed to the constructor ("
                + value + "), got " + result);
        verifyNoInteractions(result);
    }

    @Test
    public void shouldCallVisitorVisitMethod_whenAcceptCalled(@Mock ResultVisitor<Object> resultVisitor) {
        ValueExpression valueExpression = new ValueExpression(value);

        Object visitorResponse = new Object();
        when(resultVisitor.visit(valueExpression)).thenReturn(visitorResponse);

        Object result = valueExpression.accept(resultVisitor);

        assertSame(visitorResponse, result, () -> "Expected ternaryExpression to return result from resultVisitor (" +
                visitorResponse + "), got" + result);
        verify(resultVisitor, only()).visit(valueExpression);
        verifyNoInteractions(value);
    }
}
