package den.vor.easy.object.fuzzy.v3;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;

public class IntExpression {

    private int expected;

    public String generate(GenerationContext context, CustomRandom random) {
        if (context.getDepth() >= 10) {
            expected = random.nextInt();
            return expected + "";
        }
        int choice = random.nextInt(10);
        switch (choice) {
            case 0:
                throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
}
