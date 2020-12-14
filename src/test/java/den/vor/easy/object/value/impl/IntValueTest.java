package den.vor.easy.object.value.impl;


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