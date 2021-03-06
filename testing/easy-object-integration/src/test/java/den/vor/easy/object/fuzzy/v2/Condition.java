package den.vor.easy.object.fuzzy.v2;

import den.vor.easy.object.fuzzy.GenerationContext;

public interface Condition {
    
    boolean matches(GenerationContext context);
    
    
}
