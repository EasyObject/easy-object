/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
