/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.easyobject.core.parser.ast;

import io.github.easyobject.core.value.Value;
import io.github.easyobject.core.value.impl.MapValue;
import io.github.easyobject.core.value.impl.NullValue;
import io.github.easyobject.core.value.impl.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.github.easyobject.core.value.impl.StringValue.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VariablesTest {

    @Mock
    private MapValue globalVariables;
    @Mock
    private Value<?> context;
    @Mock
    private Value<?> value;
    @Mock
    private StringValue key;

    private Variables variables;

    @BeforeEach
    void setUp() {
        variables = new Variables(globalVariables, context);
    }

    @Test
    void shouldReturnValueFromGlobalVariables_whenCalledGetNullable() {
        doReturn(value).when(globalVariables).get(key);

        Value<?> actual = variables.getNullableConst(key);

        assertEquals(value, actual, () -> "Expected Variables to return " + value + ", got" + actual);
        verify(globalVariables, only()).get(key);
        verifyNoInteractions(context);
    }

    @Test
    void shouldReturnValueFromConstValues_whenCalledGetNullable() {
        Value<?> actual = variables.getNullableConst(of("pi"));

        assertNotNull(actual, () -> "Expected Variables to return pi, got" + actual);
        verifyNoInteractions(globalVariables, context);
    }

    @Test
    void shouldReturnNull_whenCalledGetNullableAndVariableDoesNotExist() {
        Value<?> actual = variables.getNullableConst(key);

        assertNull(actual, () -> "Expected Variables to return null on non-existing value, got" + actual);
        verifyNoInteractions(context);
    }

    @Test
    void shouldReturnNullValue_whenCalledGetVariableAndVariableDoesNotExist() {
        Value<?> actual = variables.getVariable(key);

        assertEquals(NullValue.NULL, actual,
                () -> "Expected Variables to return " + NullValue.NULL + " on non-existing value, got" + actual);
        verify(context, only()).get(key);
        verify(globalVariables, only()).get(key);
    }

    @Test
    void shouldReturnValueFromContext_whenCalledGetVariableAndVariableDoesNotExist() {
        doReturn(value).when(context).get(key);

        Value<?> actual = variables.getVariable(key);

        assertEquals(value, actual, () -> "Expected Variables to return " + value + ", got" + actual);
        verify(context, only()).get(key);
        verify(globalVariables, only()).get(key);
    }

}
