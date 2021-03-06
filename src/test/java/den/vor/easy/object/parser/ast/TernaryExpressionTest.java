package den.vor.easy.object.parser.ast;

import den.vor.easy.object.parser.visitors.ResultVisitor;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.BooleanValue;
import den.vor.easy.object.value.impl.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TernaryExpressionTest {

    @Mock
    private Expression condition;
    @Mock
    private Expression thenExpression;
    @Mock
    private Expression elseExpression;
    @Mock
    private Variables params;

    private TernaryExpression ternaryExpression;

    @BeforeEach
    void setUp() {
        ternaryExpression = new TernaryExpression(condition, thenExpression, elseExpression);
    }

    @Test
    public void shouldEvalConditionAndThenExpr_whenConditionIsTrue() {
        IntValue expected = IntValue.of(1);

        doReturn(BooleanValue.TRUE).when(condition).eval(params);
        doReturn(expected).when(thenExpression).eval(params);

        Value<?> result = ternaryExpression.eval(params);

        assertEquals(expected, result, () -> "Expected conditional expression to return thenExpression result (" +
                expected + "), got "+ result);
        verify(condition, only()).eval(params);
        verify(thenExpression, only()).eval(params);
        verifyNoInteractions(elseExpression);
    }

    @Test
    public void shouldEvalConditionAndElseExpr_whenConditionIsFalse() {
        IntValue expected = IntValue.of(1);

        doReturn(BooleanValue.FALSE).when(condition).eval(params);
        doReturn(expected).when(elseExpression).eval(params);

        Value<?> result = ternaryExpression.eval(params);

        assertEquals(expected, result, () -> "Expected conditional expression to return elseExpression result (" +
                expected + "), got "+ result);
        verify(condition, only()).eval(params);
        verify(elseExpression, only()).eval(params);
        verifyNoInteractions(thenExpression);
    }

    @Test
    public void shouldThrowException_whenCanNotCastConditionToBoolean() {
        doReturn(IntValue.of(1)).when(condition).eval(params);

        assertThrows(ClassCastException.class, () -> ternaryExpression.eval(params));
    }

    @Test
    public void shouldCallVisitorVisitMethod_whenAcceptCalled(@Mock ResultVisitor<Object> resultVisitor) {
        Object visitorResponse = new Object();
        when(resultVisitor.visit(ternaryExpression)).thenReturn(visitorResponse);

        Object result = ternaryExpression.accept(resultVisitor);

        assertSame(visitorResponse, result, () -> "Expected ternaryExpression to return result from resultVisitor (" +
                visitorResponse + "), got" + result);
        verify(resultVisitor, only()).visit(ternaryExpression);
        verifyNoInteractions(condition, thenExpression, elseExpression);
    }
}