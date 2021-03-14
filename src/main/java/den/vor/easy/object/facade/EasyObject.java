/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package den.vor.easy.object.facade;

import den.vor.easy.object.enums.impl.CollectionEnumValuesProvider;
import den.vor.easy.object.enums.impl.EnumEnumValuesProvider;
import den.vor.easy.object.factory.EnumFactory;
import den.vor.easy.object.factory.Factory;
import den.vor.easy.object.factory.RootFactory;
import den.vor.easy.object.factory.impl.*;
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

    public static ElFactory<Integer> isInt(String expression) {
        return ElFactory.factory(expression, Integer.class);
    }

    public static ElFactory<Object> isExpression(String expression) {
        return ElFactory.factory(expression);
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

    public static <T> ConstFactory<T> isConst(Value<T> value) {
        return new ConstFactory<>(value);
    }

    public static UUIDFactory isUUID() {
        return new UUIDFactory();
    }

    private EasyObject() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
