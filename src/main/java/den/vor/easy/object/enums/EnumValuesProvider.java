package den.vor.easy.object.enums;

import den.vor.easy.object.factory.GenerationContext;

public interface EnumValuesProvider<T> {

    T getNext(GenerationContext context);
}
