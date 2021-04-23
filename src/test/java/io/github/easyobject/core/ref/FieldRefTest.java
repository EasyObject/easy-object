/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.ref;

import io.github.easyobject.core.value.ScalarValue;
import io.github.easyobject.core.value.impl.StringValue;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.github.easyobject.core.facade.ValueFacade.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldRefTest {

    @Test
    void shouldReturnReferenceWithParentLinksOneLess_whenGetReferenceForParentFactoryCalled() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 1);

        FieldRef expected = new FieldRef(Collections.emptyList(), 0);
        FieldRef actual = fieldRef.getReferenceForParentFactory();

        assertEquals(expected, actual, () -> "Expected getReferenceForParentFactoryCalled on " + fieldRef + " to " +
                "return ref with parentLinks=0, got=" + actual);
    }

    @Test
    void shouldThrowUnsupportedOperation_whenGetReferenceForParentFactoryCalledOnRefWithZeroParentLinks() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 0);

        assertThrows(IllegalArgumentException.class, fieldRef::getReferenceForParentFactory,
                () -> "Expected getReferenceForParentFactory to throw exception when called on " + fieldRef);
    }

    @Test
    void shouldThrowUnsupportedOperation_whenGetFirstPathRefWithNonZeroParentLinks() {
        FieldRef fieldRef = new FieldRef(List.of(of("str")), 1);

        assertThrows(IllegalArgumentException.class, fieldRef::getFirstPath,
                () -> "Expected getFirstPath to throw exception when called on " + fieldRef);
    }

    @Test
    void shouldThrowUnsupportedOperation_whenGetFirstPathRefWithEmptyLinks() {
        FieldRef fieldRef = new FieldRef(Collections.emptyList(), 0);

        assertThrows(IllegalArgumentException.class, fieldRef::getFirstPath,
                () -> "Expected getFirstPath to throw exception when called on " + fieldRef);
    }

    @Test
    void shouldReturnFirstKey_whenGetFirstPath() {
        StringValue key = of("str");
        FieldRef fieldRef = new FieldRef(List.of(key), 0);

        ScalarValue<?> firstPath = fieldRef.getFirstPath();

        assertEquals(key, firstPath, () -> "Expected getFirstPath to return " + key + " when called on " + fieldRef);
    }
}
