package den.vor.easy.object.enums;

import den.vor.easy.object.factory.GenerationContext;

/**
 * Interface for objects that randomly select an object from a relatively small set
 * @param <T> type of object selected
 */
public interface EnumValuesProvider<T> {

    T getNext(GenerationContext context);
}
