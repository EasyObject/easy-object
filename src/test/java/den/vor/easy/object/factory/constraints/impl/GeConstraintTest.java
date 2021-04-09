/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.factory.constraints.impl;

import den.vor.easy.object.factory.constraints.Bound;
import den.vor.easy.object.factory.constraints.SequenceConstraintsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeConstraintTest extends ConstraintTestBase {

    @Mock
    private SequenceConstraintsValues<ComparableClass> constraintsValues;
    @Mock
    private Bound<ComparableClass> constraintBound;
    @Mock
    private ComparableClass constraintValue;
    @Mock
    private ComparableClass value;
    @Captor
    private ArgumentCaptor<Bound<ComparableClass>> argumentCaptor;

    private GeConstraint<ComparableClass> geConstraint = new GeConstraint<>("constraint");

    @BeforeEach
    void setUp() {
        when(constraintsValues.getMin()).thenReturn(constraintBound);
        when(constraintBound.getValue()).thenReturn(constraintValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void shouldNotChangeBounds_whenValueIsLessThanOrEqualsMinConstraint(int comparisonResult) {
        when(constraintsValues.getMin()).thenReturn(constraintBound);
        when(constraintBound.getValue()).thenReturn(constraintValue);
        when(value.compareTo(constraintValue)).thenReturn(comparisonResult);

        SequenceConstraintsValues<ComparableClass> result = geConstraint.apply(constraintsValues, value);

        assertSame(constraintsValues, result);
        verify(constraintsValues, never()).setMin(any());
        verify(constraintsValues, never()).setMax(any());
        verify(value, only()).compareTo(constraintValue);
        verify(constraintBound, only()).getValue();
    }

    @Test
    void shouldChangeLowerBound_whenValueIsGreaterThanMinConstraint() {
        when(constraintsValues.getMin()).thenReturn(constraintBound);
        doNothing().when(constraintsValues).setMin(argumentCaptor.capture());
        when(constraintBound.getValue()).thenReturn(constraintValue);
        when(value.compareTo(constraintValue)).thenReturn(1);

        SequenceConstraintsValues<ComparableClass> result = geConstraint.apply(constraintsValues, value);

        assertSame(constraintsValues, result);
        verify(constraintsValues).setMin(any());
        Bound<ComparableClass> newMinBound = argumentCaptor.getValue();
        assertTrue(newMinBound.isInclusive(), () -> "Expected bound=" + newMinBound + " to be inclusive");
        assertEquals(value, newMinBound.getValue(), () -> "Expected bound=" + newMinBound + " to have " +
                newMinBound + " as value");
        verify(constraintsValues, never()).setMax(any());
        verify(this.value, only()).compareTo(constraintValue);
        verify(constraintBound, only()).getValue();
    }

}
