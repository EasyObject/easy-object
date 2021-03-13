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
