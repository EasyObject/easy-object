/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy;

import den.vor.easy.object.fuzzy.factory.ElExpressionFactory;
import den.vor.easy.object.integration.util.random.RandomProviderExtension;
import den.vor.easy.object.integration.util.repeat.RepeatedElTest;
import den.vor.easy.object.integration.util.repeat.RepeatedTestExtension;
import den.vor.easy.object.parser.ExpressionEvaluator;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.value.Value;
import den.vor.easy.object.value.impl.MapValue;
import den.vor.easy.object.value.impl.NullValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({RepeatedTestExtension.class, RandomProviderExtension.class})
public class FuzzyTest {

    @RepeatedElTest(1_000)
    public void fuzzyTest(CustomRandom customRandom) {
        ElExpression<?> elExpression = ElExpressionFactory.generate(customRandom);
        String expression = elExpression.build();
        MapValue constParams = MapValue.emptyMap();
        NullValue context = NullValue.NULL;

        Value<?> value = new ExpressionEvaluator(expression, constParams).evaluate(context);

        Object resultValue = value.getValue();
        Object expected = elExpression.getExpected(new FuzzyTestContext());
        Class<?> aClass = expected.getClass();
        assertTrue(aClass.isInstance(resultValue), () -> "Expected " + expression + " to return a result of " + aClass
                + ", when evaluated with constParams=" + constParams + ", context=" + context + ", got " + value);
        Assertions.assertEquals(expected, resultValue, () -> "Expected=" + expected + " when evaluating expression='" +
                expression + "', constParams=" + constParams + ", context=" + context + ", got " + value +
                ", internal structure=" + elExpression);
    }
}
