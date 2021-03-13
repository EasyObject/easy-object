/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.fuzzy.v2;

import den.vor.easy.object.fuzzy.GenerationContext;

public interface Condition {
    
    boolean matches(GenerationContext context);
    
    
}
