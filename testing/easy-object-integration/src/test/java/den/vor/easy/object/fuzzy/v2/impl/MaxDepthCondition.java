package den.vor.easy.object.fuzzy.v2.impl;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.v2.Condition;

public class MaxDepthCondition implements Condition {
    
    private final int maxDepth;

    public MaxDepthCondition(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    
    @Override
    public boolean matches(GenerationContext context) {
        return context.getDepth() <= maxDepth;
    }
}
