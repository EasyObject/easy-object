package den.vor.easy.object.value.impl;


import den.vor.easy.object.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IntValueTest {

    @Test
    public void shouldReturnStringValueContainingInt() {
        IntValue intValue = IntValue.of(10);
        StringValue stringValue = intValue.toStringValue();

        assertThat(stringValue.getValue(), equalTo("10"));
    }
}