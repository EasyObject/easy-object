/*
 * Copyright (c) 2020-2021 Danila Varatyntsev
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.den.vor.easy.object.facade;

import com.den.vor.easy.object.enums.impl.FileEnumValuesProvider;
import com.den.vor.easy.object.exception.impl.UniqueElementsGenerationException;
import com.den.vor.easy.object.factory.EnumFactory;
import com.den.vor.easy.object.factory.Factory;
import com.den.vor.easy.object.factory.ValueSource;
import com.den.vor.easy.object.factory.impl.*;
import com.den.vor.easy.object.value.ConstValue;
import com.den.vor.easy.object.value.Value;
import com.den.vor.easy.object.value.impl.IntValue;
import com.den.vor.easy.object.enums.impl.CollectionEnumValuesProvider;
import com.den.vor.easy.object.enums.impl.EnumEnumValuesProvider;
import den.vor.easy.object.factory.impl.*;
import com.den.vor.easy.object.value.ScalarValue;
import com.den.vor.easy.object.value.impl.StringValue;

import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class that provides a convenient facade to all factories.
 */
public class EasyObject {

    private static final String ALPHABET_LOWERCASE = "abcdefghijklmnopqrsuvwxyz";
    private static final String ALPHABET_UPPERCASE = ALPHABET_LOWERCASE.toUpperCase();
    private static final String NUMBERS = "0123456789";

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
    public static <T> ListFactory<T> isList(ValueSource<T> elementFactory,
                                            ValueSource<Integer> lengthFactory) {
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
    public static <T> ListFactory<T> isList(ValueSource<T> elementFactory, int length) {
        return new ListFactory<>(elementFactory, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that generates {@link java.util.Set} values.
     * Elements are created by the {@code elementFactory}, set size is determined by {@code length}.
     * Due to the difficulty of generating unique values, factory generates up to {@code length * 10} values.
     * If it doesn't find enough unique values among them, it throws {@link UniqueElementsGenerationException}.
     *
     * @param elementFactory factory that is used to generate list elements
     * @param length         list length
     * @return list factory instance
     */
    public static <T> SetFactory<T> isSet(ValueSource<T> elementFactory, int length) {
        return new SetFactory<>(elementFactory, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that generates {@link java.util.Set} values.
     * Elements are created by the {@code elementFactory}, set size is determined by {@code lengthFactory}.
     * Due to the difficulty of generating unique values, factory generates up to {@code length * 10} values.
     * If it doesn't find enough unique values among them, it throws {@link UniqueElementsGenerationException}.
     *
     * @param elementFactory factory that is used to generate set elements
     * @param lengthFactory  factory that is used to generate set length
     * @return set factory instance
     */
    public static <T> SetFactory<T> isSet(ValueSource<T> elementFactory,
                                          ValueSource<Integer> lengthFactory) {
        return new SetFactory<>(elementFactory, lengthFactory);
    }

    /**
     * Creates a factory that generates {@link java.util.Set} values.
     * Elements are created by the {@code elementFactory}, set size is determined by {@code lengthFactory}.
     * Due to the difficulty of generating unique values,
     * factory generates up to {@code length * triesPerElement} values.
     * If it doesn't find enough unique values among them, it throws {@link UniqueElementsGenerationException}.
     *
     * @param elementFactory  factory that is used to generate set elements
     * @param lengthFactory   factory that is used to generate set length
     * @param triesPerElement coefficient that will be multiplied by required set size to determine how many values
     *                        are generated to pick unique
     * @return set factory instance
     */
    public static <T> SetFactory<T> isSet(ValueSource<T> elementFactory,
                                          ValueSource<Integer> lengthFactory,
                                          int triesPerElement) {
        return new SetFactory<>(elementFactory, lengthFactory, triesPerElement);
    }

    /**
     * Creates a factory that generates {@link java.util.Map} values.
     * Keys are created by the {@code keyFactory}, values by {@code valueFactory},
     * and the size is determined by {@code length}.
     * <p>
     * If duplicate keys are generated, only the latest is preserved. Other will be discarded, making map smaller.
     *
     * @param keyFactory   factory that is used to generate map keys
     * @param valueFactory factory that is used to generate map values
     * @param length       map size
     * @return map factory instance
     */
    public static <K, V> MapFactory<K, V> isMap(Factory<?, ? extends ScalarValue<K>> keyFactory,
                                                Factory<?, ? extends Value<V>> valueFactory,
                                                int length) {
        return new MapFactory<>(keyFactory, valueFactory, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that generates {@link java.util.Map} values.
     * Keys are created by the {@code keyFactory}, values by {@code valueFactory},
     * and the size is determined by {@code length}.
     * <p>
     * If duplicate keys are generated, only the latest is preserved. Other will be discarded, making map smaller.
     *
     * @param keyFactory    factory that is used to generate map keys
     * @param valueFactory  factory that is used to generate map values
     * @param lengthFactory factory that is used to generate set length
     * @return map factory instance
     */
    public static <K, V> MapFactory<K, V> isMap(Factory<?, ? extends ScalarValue<K>> keyFactory,
                                                Factory<?, ? extends Value<V>> valueFactory,
                                                ValueSource<Integer> lengthFactory) {
        return new MapFactory<>(keyFactory, valueFactory, lengthFactory);
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
    public static <T> PoolFactory<T> isPool(ValueSource<T> factory, int size,
                                            ValueSource<Integer> ttlFactory) {
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
    public static <T> PoolFactory<T> isPool(ValueSource<T> factory, int size, int ttl) {
        return new PoolFactory<>(factory, size, isConst(IntValue.of(ttl)));
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
     * @param <R>      corresponding wrapper type that extends {@link Value}
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
     *
     * @return UUIDv4 factory instance
     */
    public static UUIDFactory isUUID() {
        return new UUIDFactory();
    }

    /**
     * Creates a factory that returns random elements from the collection with equal probability.
     *
     * @param collection source of values
     * @param <T>        type of wrapped values
     * @param <R>        corresponding wrapper type that extends {@link Value}
     * @return enum factory instance
     */
    public static <T, R extends Value<T>> EnumFactory<T, R> isEnum(Collection<R> collection) {
        return new EnumFactory<>(new CollectionEnumValuesProvider<>(collection));
    }

    /**
     * Creates a factory that returns random elements from the file with equal probability.
     * Each line is considered to be a separate value. If you need to specify the delimiter,
     * see {@link EasyObject#isEnum(Path, String)} instead.
     *
     * @param path path of the file, that holds enum values
     * @return enum factory instance
     */
    public static EnumFactory<String, StringValue> isEnum(Path path) {
        return new EnumFactory<>(new FileEnumValuesProvider(path));
    }

    /**
     * Creates a factory that returns random elements from the file with equal probability.
     * File lines will be joined and then split by the provided delimiter.
     *
     * @param path path of the file, that holds enum values
     * @return enum factory instance
     */
    public static EnumFactory<String, StringValue> isEnum(Path path, String delimiter) {
        return new EnumFactory<>(new FileEnumValuesProvider(path));
    }

    /**
     * Creates a factory that returns random enum values with equal probability.
     * Values are wrapped with {@link ConstValue} and do not support any arithmetical or
     * logical operations in the expression language.
     *
     * @param enumClass enum to take values from
     * @param <T>       type of enum values
     * @return enum factory instance
     */
    public static <T extends Enum<T>> EnumFactory<T, ?> isEnum(Class<T> enumClass) {
        return new EnumFactory<>(new EnumEnumValuesProvider<>(enumClass));
    }

    /**
     * Creates a factory that returns objects' indexes.
     * Indexes are guaranteed to be unique in the scope of one generation.
     *
     * @param startingValue initial index
     * @param step          step between two generated indexes.
     * @return index factory instance
     */
    public static Factory<Integer, IntValue> isIndex(int startingValue, int step) {
        AtomicInteger index = new AtomicInteger(startingValue);
        return fromSupplier(() -> IntValue.of(index.getAndAdd(step)));
    }

    /**
     * Creates a factory that returns objects' indexes. Increment is 1.
     * Indexes are guaranteed to be unique in the scope of one generation.
     *
     * @param startingValue initial index
     * @return index factory instance
     */
    public static Factory<Integer, IntValue> isIndex(int startingValue) {
        return isIndex(startingValue, 1);
    }

    /**
     * Creates a factory that returns objects' indexes. Starts at 0, increment is 1.
     * Indexes are guaranteed to be unique in the scope of one generation.
     *
     * @return index factory instance
     */
    public static Factory<Integer, IntValue> isIndex() {
        return isIndex(0);
    }

    /**
     * Creates a factory that returns strings that contain specified characters.
     *
     * @param chars  characters that will be used in strings
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isString(char[] chars, int length) {
        return new StringFactory(chars, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings that contain specified characters.
     *
     * @param chars         characters that will be used in strings
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isString(char[] chars, ValueSource<Integer> lengthFactory) {
        return new StringFactory(chars, lengthFactory);
    }

    /**
     * Creates a factory that returns strings that contain specified characters.
     *
     * @param chars  characters that will be used in strings
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isString(String chars, int length) {
        return new StringFactory(chars, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings that contain specified characters.
     *
     * @param chars         characters that will be used in strings
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isString(String chars, ValueSource<Integer> lengthFactory) {
        return new StringFactory(chars, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing only alphabetic characters (a-z, A-Z).
     *
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabetic(ValueSource<Integer> lengthFactory) {
        return isString(ALPHABET_LOWERCASE + ALPHABET_UPPERCASE, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing only alphabetic characters (a-z, A-Z).
     *
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabetic(int length) {
        return isAlphabetic(isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings containing only lowercase alphabetic characters (a-z).
     *
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabeticLowercase(ValueSource<Integer> lengthFactory) {
        return isString(ALPHABET_LOWERCASE, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing only lowercase alphabetic characters (a-z).
     *
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabeticLowercase(int length) {
        return isString(ALPHABET_LOWERCASE, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings containing only uppercase alphabetic characters (A-Z).
     *
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabeticUppercase(ValueSource<Integer> lengthFactory) {
        return isString(ALPHABET_UPPERCASE, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing only uppercase alphabetic characters (A-Z).
     *
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphabeticUppercase(int length) {
        return isString(ALPHABET_UPPERCASE, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings containing only numbers (0-9).
     *
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isNumericString(ValueSource<Integer> lengthFactory) {
        return isString(NUMBERS, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing only numbers (0-9).
     *
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isNumericString(int length) {
        return isString(NUMBERS, isConst(IntValue.of(length)));
    }

    /**
     * Creates a factory that returns strings containing alphabetic and numeric characters (a-z, A-Z, 0-9).
     *
     * @param lengthFactory factory that determines the length of the strings.
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphanumeric(ValueSource<Integer> lengthFactory) {
        return isString(ALPHABET_LOWERCASE + ALPHABET_UPPERCASE + NUMBERS, lengthFactory);
    }

    /**
     * Creates a factory that returns strings containing alphabetic and numeric characters (a-z, A-Z, 0-9).
     *
     * @param length string length
     * @return string factory instance
     */
    public static Factory<String, StringValue> isAlphanumeric(int length) {
        return isString(ALPHABET_LOWERCASE + ALPHABET_UPPERCASE + NUMBERS, isConst(IntValue.of(length)));
    }

    private EasyObject() {
        // This is a class with static members only. There is no need to create instances of this class
    }
}
