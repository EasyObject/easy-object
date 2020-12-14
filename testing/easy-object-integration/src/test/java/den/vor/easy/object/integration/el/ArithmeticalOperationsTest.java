package den.vor.easy.object.integration.el;

import den.vor.easy.object.integration.util.random.RandomProviderExtension;
import den.vor.easy.object.integration.util.repeat.RepeatedElTest;
import den.vor.easy.object.integration.util.repeat.RepeatedTestExtension;
import den.vor.easy.object.random.CustomRandom;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RepeatedTestExtension.class, RandomProviderExtension.class})
public class ArithmeticalOperationsTest {

    @Nested
    public class SumTest {

        @RepeatedElTest
        public void shouldSumTwoIntsWithoutOverflow(CustomRandom random) {
            int firstInt = random.nextInt(-1_000_000_000, 1_000_000_000);
            int secondInt = random.nextInt(-1_000_000_000, 1_000_000_000);
            TestEvaluator.evaluate(firstInt + " + " + secondInt).assertEquals(firstInt + secondInt);
        }

        @RepeatedElTest
        public void shouldSumTwoIntsWithPositiveOverflow(CustomRandom random) {
            int firstInt = random.nextInt(1_500_000_000, 2_000_000_000);
            int secondInt = random.nextInt(1_500_000_000, 2_000_000_000);
            TestEvaluator.evaluate(firstInt + " + " + secondInt).assertEquals(firstInt + secondInt);
        }

        @RepeatedElTest
        public void shouldSumTwoIntsWithNegativeOverflow(CustomRandom random) {
            int firstInt = random.nextInt(-2_000_000_000, -1_500_000_000);
            int secondInt = random.nextInt(-2_000_000_000, -1_500_000_000);
            TestEvaluator.evaluate(firstInt + " + " + secondInt).assertEquals(firstInt + secondInt);
        }

        @RepeatedElTest
        public void shouldSumTwoDoubles(CustomRandom random) {
            double firstInt = random.nextDouble();
            double secondInt = random.nextDouble();
            TestEvaluator.evaluate(firstInt + " + " + secondInt).assertEquals(firstInt + secondInt);
        }
    }
}
