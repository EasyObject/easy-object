package den.vor.easy.object.ref;

import den.vor.easy.object.value.ScalarValue;
import den.vor.easy.object.value.impl.StringValue;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static den.vor.easy.object.facade.ValueFacade.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldRefTest {

    @Test
    public void shouldReturnReferenceWithParentLinksOneLess_whenGetReferenceForParentFactoryCalled() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 1);

        FieldRef expected = new FieldRef(Collections.emptyList(), 0);
        FieldRef actual = fieldRef.getReferenceForParentFactory();

        assertEquals(expected, actual, () -> "Expected getReferenceForParentFactoryCalled on " + fieldRef + " to " +
                "return ref with parentLinks=0, got=" + actual);
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenGetReferenceForParentFactoryCalledOnRefWithZeroParentLinks() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 0);

        assertThrows(IllegalArgumentException.class, fieldRef::getReferenceForParentFactory,
                () -> "Expected getReferenceForParentFactory to throw exception when called on " + fieldRef);
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenGetFirstPathRefWithNonZeroParentLinks() {
        FieldRef fieldRef = new FieldRef(List.of(of("str")), 1);

        assertThrows(IllegalArgumentException.class, fieldRef::getFirstPath,
                () -> "Expected getFirstPath to throw exception when called on " + fieldRef);
    }

    @Test
    public void shouldThrowUnsupportedOperation_whenGetFirstPathRefWithEmptyLinks() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 0);

        assertThrows(IllegalArgumentException.class, fieldRef::getFirstPath,
                () -> "Expected getFirstPath to throw exception when called on " + fieldRef);
    }

    @Test
    public void shouldReturnFirstKey_whenGetFirstPath() {
        StringValue key = of("str");
        FieldRef fieldRef = new FieldRef(List.of(key), 0);

        ScalarValue<?> firstPath = fieldRef.getFirstPath();

        assertEquals(key, firstPath, () -> "Expected getFirstPath to return " + key + " when called on " + fieldRef);
    }
}