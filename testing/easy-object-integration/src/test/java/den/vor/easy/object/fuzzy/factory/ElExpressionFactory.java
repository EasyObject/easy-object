package den.vor.easy.object.fuzzy.factory;

import den.vor.easy.object.fuzzy.ElExpression;
import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.random.CustomRandom;
import den.vor.easy.object.random.impl.XorShift1024StarProvider;

public class ElExpressionFactory {

    public static ElExpression<?> generate() {
        CustomRandom customRandom = new XorShift1024StarProvider().getRandom();
        return generate(customRandom);
    }

    public static ElExpression<?> generate(CustomRandom customRandom) {
        GenerationContext context = new GenerationContext();
        return IntExpressionFactory.getExpression(context, customRandom);
    }
}
