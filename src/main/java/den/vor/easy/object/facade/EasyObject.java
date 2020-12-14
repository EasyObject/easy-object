package den.vor.easy.object.facade;

import den.vor.easy.object.enums.impl.CollectionEnumValuesProvider;
import den.vor.easy.object.enums.impl.EnumEnumValuesProvider;
import den.vor.easy.object.factory.EnumFactory;
import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.RootFactory;
import den.vor.easy.object.factory.impl.IntFactory;
import den.vor.easy.object.factory.impl.ObjectFactory;
import den.vor.easy.object.factory.impl.SupplierFactory;
import den.vor.easy.object.value.ConstValue;
import den.vor.easy.object.value.Value;

import java.util.Collection;
import java.util.function.Supplier;

public class EasyObject {

    public static <T extends Value<?>> RootFactory<T> root(Factory<?, T> factory) {
        return new RootFactory<>(factory, Integer.MAX_VALUE);
    }

    public static ObjectFactory isObject() {
        return new ObjectFactory();
    }

    public static ObjectFactory isObject(String name, Factory<?, ?> factory) {
        return new ObjectFactory().and(name, factory);
    }

    public static IntFactory isInt() {
        return new IntFactory();
    }

    public static IntFactory isInt(int min, int max) {
        return new IntFactory(min, max);
    }

    public static <T, R extends Value<T>> SupplierFactory<T, R> fromSupplier(Supplier<R> supplier) {
        return new SupplierFactory<>(supplier);
    }

    public static <T, R extends Value<T>> EnumFactory<T, R> isEnum(Collection<R> collection) {
        return new EnumFactory<>(new CollectionEnumValuesProvider<>(collection));
    }

    public static <T extends Enum<T>> EnumFactory<T, ?> isEnum(Class<T> enumClass) {
        return new EnumFactory<>(new EnumEnumValuesProvider<>(enumClass));
    }
}
