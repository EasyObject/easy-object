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
import den.vor.easy.object.factory.impl.*;
import den.vor.easy.object.value.Value;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static den.vor.easy.object.value.impl.IntValue.of;

/**
 * Utility class that provides a convenient facade to all factories.
 */
public class EasyObject {

    /**
     * Creates a factory that generates {@link BigInteger} values.
     * Bounds are set to default - [0, ffffffff_ffffffff_ffffffff_ffffffff].
     *
     * @return big integer factory instance
     */
    public static BigIntFactory isBigInt() {
        return new BigIntFactory();
    }

    /**
     * Creates a factory that generates {@link BigInteger} values within provided bounds.
     * Bounds are <b>inclusive</b>
     *
     * @param min lower bound
     * @param max upper bound
     * @return big integer factory instance
     */
    public static BigIntFactory isBigInt(BigInteger min, BigInteger max) {
        return new BigIntFactory(min, max);
    }

    /**
     * Creates a factory that generates {@link BigInteger} values from the provided expression.
     * Expression result is cast to the target class. If you don't need this behaviour, see
     * {@link EasyObject#isExpression(String)} instead.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<BigInteger> isBigInt(String expression) {
        return ElFactory.factory(expression, BigInteger.class);
    }

    /**
     * Creates a factory that always returns the provided value.
     *
     * @param value value to return
     * @return constant value factory instance.
     */
    public static <T> ConstFactory<T> isConst(Value<T> value) {
        return new ConstFactory<>(value);
    }

    /**
     * Creates a factory that generates {@link LocalDate} values.
     * Bounds are set to defaults - [1970-01-01, 2099-12-31].
     *
     * @return local date factory instance
     */
    public static DateFactory isDate() {
        return new DateFactory();
    }

    /**
     * Creates a factory that generates {@link LocalDate} values within provided bounds.
     * Bounds are <b>inclusive</b>.
     *
     * @param min lower bound
     * @param max upper bound
     * @return local date factory instance
     */
    public static DateFactory isDate(LocalDate min, LocalDate max) {
        return new DateFactory(min, max);
    }

    /**
     * Creates a factory that generates {@link LocalDate} values from the provided expression.
     * Expression result is cast to the target class. If you don't need this behaviour, see
     * {@link EasyObject#isExpression(String)} instead.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<LocalDate> isDate(String expression) {
        return ElFactory.factory(expression, LocalDate.class);
    }

    /**
     * Creates a factory that generates {@link LocalDateTime} values.
     * Bounds are set to defaults - [1970-01-01 00:00:00:000000, 2099-12-31 23:59:59:999999].
     *
     * @return local date time factory instance
     */
    public static DatetimeFactory isDateTime() {
        return new DatetimeFactory();
    }

    /**
     * Creates a factory that generates {@link LocalDateTime} values within provided bounds.
     * Bounds are <b>inclusive</b>.
     *
     * @param min lower bound
     * @param max upper bound
     * @return local date time factory instance
     */
    public static DatetimeFactory isDateTime(LocalDateTime min, LocalDateTime max) {
        return new DatetimeFactory(min, max);
    }

    /**
     * Creates a factory that generates {@link LocalDateTime} values from the provided expression.
     * Expression result is cast to the target class. If you don't need this behaviour, see
     * {@link EasyObject#isExpression(String)} instead.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<LocalDateTime> isDateTime(String expression) {
        return ElFactory.factory(expression, LocalDateTime.class);
    }

    /**
     * Creates a factory that generates values from the provided expression.
     * Expression result type is not checked, if you need a check use {@code isXXX(String)} methods.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<Object> isExpression(String expression) {
        return ElFactory.factory(expression);
    }

    /**
     * Creates a factory that generates {@link Integer} values.
     * Bounds are set to defaults - [-1_000_000, 1_000_000].
     *
     * @return integer factory instance
     */
    public static IntFactory isInt() {
        return new IntFactory();
    }

    /**
     * Creates a factory that generates {@link Integer} values within provided bounds.
     * Bounds are <b>inclusive</b>.
     *
     * @param min lower bound
     * @param max upper bound
     * @return integer factory instance
     */
    public static IntFactory isInt(int min, int max) {
        return new IntFactory(min, max);
    }

    /**
     * Creates a factory that generates {@link Integer} values from the provided expression.
     * Expression result is cast to the target class. If you don't need this behaviour, see
     * {@link EasyObject#isExpression(String)} instead.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<Integer> isInt(String expression) {
        return ElFactory.factory(expression, Integer.class);
    }

    /**
     * Creates a factory that generates {@link java.util.List} values.
     * Elements are created by the {@code elementFactory}, list length is determined by {@code lengthFactory}.
     *
     * @param elementFactory factory that is used to generate list elements
     * @param lengthFactory  factory that is used to generate list length
     * @return list factory instance
     */
    public static <T> ListFactory<T> isList(Factory<T, ? extends Value<T>> elementFactory,
                                            Factory<Integer, ? extends Value<Integer>> lengthFactory) {
        return new ListFactory<>(elementFactory, lengthFactory);
    }

    /**
     * Creates a factory that generates {@link java.util.List} values.
     * Elements are created by the {@code elementFactory}, list length is determined by {@code length}.
     *
     * @param elementFactory factory that is used to generate list elements
     * @param length         list length
     * @return list factory instance
     */
    public static <T> ListFactory<T> isList(Factory<T, ? extends Value<T>> elementFactory, int length) {
        return new ListFactory<>(elementFactory, isConst(of(length)));
    }

    /**
     * Creates a factory that generates values using the provided factory and maps them to another type using provided
     * mapping function.
     * Usage example: {@code isMapped(isInt(), IntValue::toStringValue)} to generate numeric strings.
     *
     * @param factory         factory that generates base values
     * @param mappingFunction mapping function to be applied to values generated by the factory
     * @return mapping factory instance
     */
    public static <T, P extends Value<?>> MappingFactory<T, P> isMapped(Factory<?, P> factory,
                                                                        Function<P, Value<T>> mappingFunction) {
        return new MappingFactory<>(factory, mappingFunction);
    }

    /**
     * Creates a factory that generates objects.
     * Each object may have an arbitrary number of fields, which values are generated by nested factories.
     * See {@link ObjectFactory} for details.
     *
     * @return object factory instance
     */
    public static ObjectFactory isObject() {
        return new ObjectFactory();
    }

    /**
     * Creates a factory that generates objects and initializes one field.
     * Each object may have an arbitrary number of fields, which values are generated by nested factories.
     * See {@link ObjectFactory} for details.
     *
     * @param name    field name
     * @param factory factory that generates values for the field
     * @return object factory instance
     */
    public static ObjectFactory isObject(String name, Factory<?, ?> factory) {
        return new ObjectFactory().and(name, factory);
    }

    /**
     * Creates a factory that pools values.
     * Factory returns a random value from the pool with equal probability. Each pool entry has an associated
     * time to live - number of times it will be returned by the factory before been replaced.
     * If you need an infinite TTL use {@link EasyObject#isEnum(Collection)} for better performance.
     *
     * @param factory    factory that is used to generate pool entry
     * @param size       pool size
     * @param ttlFactory factory that specifies how many time each entry can be returned
     * @param <T>        type of value generated by the factory
     * @return pool factory instance
     */
    public static <T> PoolFactory<T> isPool(Factory<T, Value<T>> factory, int size,
                                            Factory<Integer, ? extends Value<Integer>> ttlFactory) {
        return new PoolFactory<>(factory, size, ttlFactory);
    }

    /**
     * Creates a factory that pools values.
     * Factory returns a random value from the pool with equal probability. Each pool entry has an associated
     * time to live - number of times it will be returned by the factory before been replaced.
     * If you need an infinite TTL use {@link EasyObject#isEnum(Collection)} for better performance.
     *
     * @param factory factory that is used to generate pool entry
     * @param size    pool size
     * @param ttl     how many time each entry can be returned
     * @param <T>     type of value generated by the factory
     * @return pool factory instance
     */
    public static <T> PoolFactory<T> isPool(Factory<T, Value<T>> factory, int size, int ttl) {
        return new PoolFactory<>(factory, size, isConst(of(ttl)));
    }

    /**
     * Creates a factory that returns provided values one by one. When reaches the end, starts from the very beginning.
     *
     * @param values values to return
     * @param <T>    type of wrapped object
     * @return round robin factory instance
     */
    public static <T> RoundRobinFactory<T> isRoundRobin(List<Value<T>> values) {
        return new RoundRobinFactory<>(values);
    }

    /**
     * Creates a factory that returns provided values one by one. When reaches the end, starts from the very beginning.
     *
     * @param values values to return
     * @param <T>    type of wrapped object
     * @return round robin factory instance
     */
    public static <T> RoundRobinFactory<T> isRoundRobin(Value<T>... values) {
        return new RoundRobinFactory<>(values);
    }

    /**
     * Creates a factory that uses supplier to generate objects.
     * Usage example: {@code fromSupplier(() -> myFunc())}.
     *
     * @param supplier supplier to get values from
     * @param <T>      type of generated values
     * @param <R>      corresponding wrapper type that extends {@link Value<T>}
     * @return supplier factory instance.
     */
    public static <T, R extends Value<T>> SupplierFactory<T, R> fromSupplier(Supplier<R> supplier) {
        return new SupplierFactory<>(supplier);
    }

    /**
     * Creates a factory that generates {@link LocalTime} values.
     * Bounds are set to defaults - [00:00:00:000000, 23:59:59:999999].
     *
     * @return local time factory instance
     */
    public static TimeFactory isTime() {
        return new TimeFactory();
    }

    /**
     * Creates a factory that generates {@link LocalTime} values within provided bounds.
     * Bounds are <b>inclusive</b>.
     *
     * @param min lower bound
     * @param max upper bound
     * @return local time factory instance
     */
    public static TimeFactory isTime(LocalTime min, LocalTime max) {
        return new TimeFactory(min, max);
    }

    /**
     * Creates a factory that generates {@link LocalTime} values from the provided expression.
     * Expression result is cast to the target class. If you don't need this behaviour, see
     * {@link EasyObject#isExpression(String)} instead.
     *
     * @param expression expression to evaluate during generation
     * @return expression language factory instance
     */
    public static ElFactory<LocalTime> isTime(String expression) {
        return ElFactory.factory(expression, LocalTime.class);
    }

    /**
     * Creates a factory that generates UUIDv4 values.
     * @return UUIDv4 factory instance
     */
    public static UUIDFactory isUUID() {
        return new UUIDFactory();
    }

    /**
     * Creates a factory that returns random elements from the collection with equal probability.
     *
     * @param collection source of values
     * @param <T> type of wrapped values
     * @param <R> corresponding wrapper type that extends {@link Value<T>}
     * @return enum factory instance
     */
    public static <T, R extends Value<T>> EnumFactory<T, R> isEnum(Collection<R> collection) {
        return new EnumFactory<>(new CollectionEnumValuesProvider<>(collection));
    }

    /**
     * Creates a factory that returns random enum values with equal probability.
     * Values are wrapped with {@link den.vor.easy.object.value.ConstValue} and do not support any arithmetical or
     * logical operations in the expression language.
     *
     * @param enumClass enum to take values from
     * @param <T> type of enum values
     * @return enum factory instance
     */
    public static <T extends Enum<T>> EnumFactory<T, ?> isEnum(Class<T> enumClass) {
        return new EnumFactory<>(new EnumEnumValuesProvider<>(enumClass));
    }

    private EasyObject() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
