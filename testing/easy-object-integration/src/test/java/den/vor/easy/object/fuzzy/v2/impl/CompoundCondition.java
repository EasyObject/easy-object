package den.vor.easy.object.fuzzy.v2.impl;

import den.vor.easy.object.fuzzy.GenerationContext;
import den.vor.easy.object.fuzzy.v2.Condition;

import java.util.Arrays;
import java.util.List;

public class CompoundCondition implements Condition {
    
    private final List<Condition> conditions;

    public CompoundCondition(Condition[] conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    @Override
    public boolean matches(GenerationContext context) {
        return conditions.stream().allMatch(condition -> condition.matches(context));
    }
}
