package org.sirenia.func;

import org.sirenia.func.anno.Pure;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.interceptor.BuddyInterceptor;
import org.sirenia.func.interceptor.ProxyHandler;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.mutable.MutableInt;
import org.sirenia.func.tuple.Tuple2;
import org.springframework.util.Assert;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import static org.sirenia.func.core.DateFunc.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.google.common.base.Suppliers;
import org.sirenia.func.util.Functions;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import org.sirenia.func.util.IOs;
import org.sirenia.func.dict.Dict;
import org.sirenia.func.tuple.Tuple0;
import org.sirenia.func.tuple.Tuple1;
import org.sirenia.func.tuple.Tuple10;
import org.sirenia.func.tuple.Tuple3;
import org.sirenia.func.tuple.Tuple4;
import org.sirenia.func.tuple.Tuple5;
import org.sirenia.func.tuple.Tuple6;
import org.sirenia.func.tuple.Tuple7;
import org.sirenia.func.tuple.Tuple8;
import org.sirenia.func.tuple.Tuple9;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.util.SerializationUtils;
import org.sirenia.func.util.Reflections;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import static org.sirenia.func.core.TextFunc.*;
import com.google.common.base.Ascii;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import org.sirenia.func.util.Threads;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import lombok.Cleanup;
import org.springframework.util.FileCopyUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class Func{

    @Pure
    public static @Nonnull <T> T[] arrayOf(@Nonnull Class<T> clazz, @Nullable T... items){
        if(items == null){
            return (T[]) Array.newInstance(clazz,0);
        }
        T[] array = (T[]) Array.newInstance(clazz,items.length);
        System.arraycopy(items,0,array,0,items.length);
        return array;
    }
    @Pure
    public static @Nonnull <T> T[] newArray(@Nonnull Class<T> clazz, int size){
        T[] array = (T[]) Array.newInstance(clazz,size);
        return array;
    }
    @Pure
    public static @Nonnull <T> Stream<T> stream(@Nonnull T[] array) {
        return Arrays.stream(array);
    }
    /**
     * travel array elements with index
     */
    @Pure
    public static <T> void eachWithIndex(@Nonnull T[] arr, @Nonnull BiConsumer<Integer, T> fun) {
        for (int i = 0; i < arr.length; i++) {
            fun.accept(i, arr[i]);
        }
    }

    /**
     * proxy a class, supports interface and class.
     * it will proxy static and non-static method!
     */
    @SneakyThrows
    @SideEffect
    public static @Nonnull
    <T> Class<? extends T> proxy(@Nonnull Class<T> targetClass, @Nullable Predicate<Method> filter, @Nonnull ProxyHandler handler) {
        return BuddyInterceptor.proxy(targetClass, filter, handler);
    }
    @SneakyThrows
    @SideEffect
    public static @Nonnull
    <T> Class<? extends T> proxy(@Nonnull Class<T> targetClass, @Nonnull ProxyHandler handler) {
        return BuddyInterceptor.proxy(targetClass, handler);
    }

    /**
     * fast create a list.
     */
    @Pure
    public static @Nonnull
    <T> List<T> listOf(@Nullable T... items) {
        List<T> list = new LinkedList<>();
        if (items == null) {
            return list;
        }
        for (T item : items) {
            list.add(item);
        }
        return list;
    }
    /**
     * fast create a set.
     */
    @Pure
    public static @Nonnull
    <T> Set<T> setOf(T... items) {
        Set<T> set = new HashSet<>();
        if (items == null) {
            return set;
        }
        for (T item : items) {
            set.add(item);
        }
        return set;
    }
    /**
     * fast create a map.
     */
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf() {
        return new LinkedHashMap<>();
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        map.put(k10, v10);
        return map;
    }
    /**
     * fast create a map.
     */
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(@Nullable Map<K, V> origMap) {
        Map<K, V> map = new LinkedHashMap<>();
        if (origMap != null) {
            map.putAll(origMap);
        }
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> zip(@Nonnull Iterable<K> keys, @Nonnull Iterable<V> values) {
        Map<K, V> map = new LinkedHashMap<>();
        Iterator<K> keysIter = keys.iterator();
        Iterator<V> valuesIter = values.iterator();
        while (keysIter.hasNext()) {
            if (valuesIter.hasNext()) {
                map.put(keysIter.next(), valuesIter.next());
            } else {
                map.put(keysIter.next(), null);
            }
        }
        return map;
    }
    @Pure
    public static @Nonnull
    <K, V> Tuple2<List<K>, List<V>> unzip(@Nonnull Map<K, V> map) {
        List<K> keys = listOf();
        List<V> values = listOf();
        map.forEach((k, v) -> {
            keys.add(k);
            values.add(v);
        });
        return new Tuple2<>(keys, values);
    }
    /**
     * different with list#addAll, concat is a pure function
     */
    @Pure
    public static @Nonnull
    <T> List<T> concat(@Nullable Iterable<T> list1, @Nullable Iterable<T>... otherLists) {
        List<T> ret = new LinkedList<>();
        if (list1 != null) {
            for (T t : list1) {
                ret.add(t);
            }
        }
        if (otherLists != null) {
            for (Iterable<T> list : otherLists) {
                if (list != null) {
                    for (T t : list) {
                        ret.add(t);
                    }
                }
            }
        }
        return ret;
    }
    @Pure
    public static @Nonnull
    <K,V> Map<K,V> concat(@Nullable Map<K,V> map1, @Nullable Map<K,V>... others) {
        Map<K,V> map = mapOf();
        if(map1!=null){
            map.putAll(map1);
        }
        if(others!=null){
            for(Map<K,V> it: others){
                if(it!=null){
                    map.putAll(it);
                }
            }
        }
        return map;
    }
    /**
     * different with Collections#reverse, reverse is a pure function
     */
    @Pure
    public static @Nullable
    <T> List<T> reverse(@Nullable List<T> list) {
        if (list == null) {
            return null;
        }
        LinkedList<T> linkedList = new LinkedList<>();
        for (T t : list) {
            linkedList.addFirst(t);
        }
        return linkedList;
    }
    /**
     * inverse key and value
     */
    @Pure
    public static @Nonnull
    <K, V> Map<V, K> inverse(@Nullable Map<K, V> map) {
        if (map == null) {
            return null;
        }
        Map<V, K> newMap = new LinkedHashMap<>();
        map.forEach((k, v) -> newMap.put(v, k));
        return newMap;
    }
    /**
     * slice
     * different with list#subList, slice support negative index.
     */
    @Pure
    public static @Nonnull
    <T> List<T> slice(@Nonnull List<T> list, int startInclusion, int endInclusion) {
        int size = list.size();
        if (startInclusion < 0) {
            startInclusion += size;
        }
        if (endInclusion < 0) {
            endInclusion += size;
        }
        return list.subList(startInclusion, endInclusion+1);
    }
    @Pure
    public static @Nonnull
    <T> Set<T> diff(@Nonnull Iterable<T> c1, @Nonnull Iterable<T> c2) {
        Set<T> set1 = castOrConvert(c1);
        Set<T> set2 = castOrConvert(c2);
        return Sets.difference(set1, set2);
    }
    @Pure
    public static @Nonnull
    <T> Set<T> intersect(@Nonnull Iterable<T> c1, @Nonnull Iterable<T> c2) {
        Set<T> set1 = castOrConvert(c1);
        Set<T> set2 = castOrConvert(c2);
        return Sets.intersection(set1, set2);
    }
    @Pure
    public static @Nonnull
    <T> Set<T> union(@Nonnull Iterable<T> c1, @Nonnull Iterable<T> c2) {
        Set<T> set1 = castOrConvert(c1);
        Set<T> set2 = castOrConvert(c2);
        return Sets.union(set1, set2);
    }
    private static <T> Set<T> castOrConvert(Iterable<T> iter){
        if (iter instanceof Set) {
            return cast(iter);
        } else {
            Set<T> set = setOf();
            for (T e : iter) {
                set.add(e);
            }
            return set;
        }
    }
    @Pure
    public static <T> void eachWithIndex(@Nonnull Iterable<T> iterable, @Nonnull BiConsumer<Integer, T> fun) {
        Iterator<T> iter = iterable.iterator();
        int i = 0;
        while (iter.hasNext()) {
            fun.accept(i++, iter.next());
        }
    }
    @Pure
    public static @Nonnull
    Stream<Integer> range(int end) {
        return range(0, end, 1);
    }
    @Pure
    public static @Nonnull
    Stream<Integer> range(int start, int end) {
        return range(start, end, 1);
    }
    @Pure
    public static @Nonnull
    Stream<Integer> range(int start, int end, int step) {
        Assert.isTrue(step > 0, "step must bigger than 0.");
        if (start > end) {
            step = -step;
        }
        return rangeInternal(start, end, step);
    }
    private static Stream<Integer> rangeInternal(int start, int end, int step) {
        MutableInt mutableInt = new MutableInt();
        mutableInt.setValue(start);
        Supplier<Integer> supplier = () -> mutableInt.getAndAdd(step);
        int size = (int)Math.ceil((end - start+0.0 )/ step);
        return Stream.generate(supplier).limit(size);
    }
    @Pure
    public static @Nonnull
    Stream<Integer> rangeTo(int start, int end, int step) {
        Assert.isTrue(step > 0, "step must bigger than 0.");
        if (start > end) {
            step = -step;
        }
        return rangeInternal(start, end + step, step);
    }
    @Pure
    public static @Nonnull
    Stream<Integer> rangeTo(int end) {
        return rangeTo(0, end, 1);
    }
    @Pure
    public static @Nonnull
    Stream<Integer> rangeTo(int start, int end) {
        return rangeTo(start, end, 1);
    }
    @Pure
    public static @Nonnull
    Stream<Character> range(char start, char end, int step) {
        Assert.isTrue(step > 0, "step must bigger than 0.");
        if (start > end) {
            step = -step;
        }
        return rangeInternal(start, end, step);
    }
    private static Stream<Character> rangeInternal(char start, char end, int step) {
        MutableInt mutableInt = new MutableInt();
        mutableInt.setValue(start);
        Supplier<Character> supplier = () -> (char) mutableInt.getAndAdd(step);
        int size = (int)Math.ceil((end - start+0.0 )/ step);
        return Stream.generate(supplier).limit(size);
    }
    @Pure
    public static @Nonnull
    Stream<Character> rangeTo(char start, char end, int step) {
        Assert.isTrue(step > 0, "step must bigger than 0.");
        if (start > end) {
            step = -step;
        }
        return rangeInternal(start, (char) (end + step), step);
    }
    @Pure
    public static @Nonnull
    <K, V> Stream<Map.Entry<K, V>> stream(@Nonnull Map<K, V> map) {
        return map.entrySet().stream();
    }
    @Pure
    public static <T> List<List<T>> chunk(@Nonnull List<T> list, int size) {
        int count = 0;
        List<List<T>> ret = listOf();
        List<T> current = null;
        for (T t : list) {
            if (count % size == 0) {
                current = listOf();
                ret.add(current);
            }
            current.add(t);
            count++;
        }
        return ret;
    }
    @Pure
    public static @Nonnull <T> List<T> flatten(@Nonnull List<?> list){
        List<T> acc = listOf();
        for(Object it : list){
            flatten(it,acc);
        }
        return acc;
    }
    private static <T> void flatten(Object obj,List<T> acc){
        if(isInstanceOf(obj,List.class)){
            acc.addAll(flatten(cast(obj)));
        }else{
            acc.add(cast(obj));
        }
    }

    private static DateTimeFormatter ofPattern(String pattern){
        DateTimeFormatter fmt = formatters.get(pattern);
        if(fmt == null){
            fmt = DateTimeFormatter.ofPattern(pattern);
            formatters.putIfAbsent(pattern,fmt);
        }
        return fmt;
    }
    //format
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDate date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDate date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDateTime date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalTime date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalTime date) {
        return DateTimeFormatter.ISO_LOCAL_TIME.format(date);
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull Date date) {
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        //return format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull Date date, @Nonnull String pattern) {
        return date.toInstant().atZone(ZoneId.systemDefault()).format(ofPattern(pattern));
    }
    @Pure
    //toLocalDate
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    @Pure
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull String date, @Nonnull String pattern) {
        return LocalDate.parse(date, ofPattern(pattern));
    }
    @Pure
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    //toLocalDateTime
    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull String date, @Nonnull String pattern) {
        return LocalDateTime.parse(date, ofPattern(pattern));
    }
    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    //toDate
    @Pure
    public static @Nonnull
    Date toDate(@Nonnull String date) {
        return toDate(toLocalDateTime(date));
    }
    @Pure
    public static @Nonnull
    Date toDate(@Nonnull String date, @Nonnull String pattern) {
        return toDate(toLocalDateTime(date, pattern));
    }
    @Pure
    public static @Nonnull
    Date toDate(@Nonnull LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    @Pure
    public static @Nonnull
    Date toDate(@Nonnull LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Pure
    public static @Nonnull <E extends Enum<E>> Map<String,E> enumMap(@Nonnull Class<E> enumClass) {
        final Map<String, E> map = new LinkedHashMap<>();
        for (final E e: enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * we can define lazyinit field
     */
    @Pure
    public static @Nonnull <T> Supplier<T> memoize(@Nonnull Supplier<T> supplier) {
        return Suppliers.memoize(() -> supplier.get());
    }
    /**
     * we can define lazyinit field with Expiration
     * such as openapi, the token has a expire time.
     */
    @Pure
    public static @Nonnull <T> Supplier<T> memoizeWithExpiration(@Nonnull Supplier<T> supplier, long duration, @Nonnull TimeUnit unit) {
        return Suppliers.memoizeWithExpiration(() -> supplier.get(), duration, unit);
    }
    /**
     * we must catch Checked Exception in function body.
     * <p>
     * such as:
     * <p>
     * listOf(".").forEach(it -> {
     * try {
     * List<String> lines = Files.readAllLines(Paths.get(it), StandardCharsets.UTF_8);
     * ...
     * } catch (IOException e) {
     * throw new RuntimeException(e);
     * }
     * });
     * <p>
     * now we can write like this:
     * <p>
     * listOf(".").forEach(sneakyConsumer(it ->
     * Files.readAllLines(Paths.get(it), StandardCharsets.UTF_8)
     * ...
     * ));
     */
    @Pure
    public static @Nonnull <T, R> Function<T, R> sneakyFunction(@Nonnull Functions.Function0<T, R> f) {
        return new Function<T, R>() {
            @Override
            @SneakyThrows
            public R apply(T t) {
                return f.apply(t);
            }
        };
    }
    @Pure
    public static @Nonnull <T> Consumer<T> sneakyConsumer(@Nonnull Functions.Consumer0<T> c) {
        return new Consumer<T>() {
            @SneakyThrows
            @Override
            public void accept(T t) {
                c.accept(t);
            }
        };
    }
    @Pure
    public static @Nonnull <T> Supplier<T> sneakySupplier(@Nonnull Functions.Supplier0<T> s) {
        return new Supplier<T>() {
            @SneakyThrows
            @Override
            public T get() {
                return s.get();
            }
        };
    }
    @Pure
    public static @Nonnull <T> Predicate<T> sneakyPredicate(@Nonnull Functions.Predicate0<T> p) {
        return new Predicate<T>() {
            @SneakyThrows
            @Override
            public boolean test(T t) {
                return p.test(t);
            }
        };
    }
    @Pure
    public static @Nonnull <T, U, R> BiFunction<T, U, R> sneakyBiFunction(@Nonnull Functions.BiFunction0<T, U, R> f) {
        return new BiFunction<T, U, R>() {
            @Override
            @SneakyThrows
            public R apply(T t, U u) {
                return f.apply(t, u);
            }
        };
    }
    @Pure
    public static @Nonnull <T, U> BiConsumer<T, U> sneakyBiConsumer(@Nonnull Functions.BiConsumer0<T, U> c) {
        return new BiConsumer<T, U>() {
            @SneakyThrows
            @Override
            public void accept(T t, U u) {
                c.accept(t, u);
            }
        };
    }
    @Pure
    public static @Nonnull <T, U> BiPredicate<T, U> sneakyBiPredicate(@Nonnull Functions.BiPredicate0<T, U> p) {
        return new BiPredicate<T, U>() {
            @SneakyThrows
            @Override
            public boolean test(T t, U u) {
                return p.test(t, u);
            }
        };
    }

    @SneakyThrows
    @SideEffect
    public static @Nonnull String readLine() {
        return IOs.stdReader().readLine();
    }
    @SneakyThrows
    @SideEffect
    public static @Nonnull String readLine(@Nullable String msg) {
        println(msg);
        return IOs.stdReader().readLine();
    }
    @SideEffect
    public static void println(@Nullable Object obj) {
        System.out.println(format("%s", obj));
    }
    /**
     * the function System.out.printf is too strict.
     * if number of placeholder more than number of args,
     * it will throw a exception.
     * this is a relaxed version of System.out.printf
     */
    @SideEffect
    public static void printf(@Nonnull String template, @Nullable Object... args) {
        System.out.println(format(template, args));
    }

    /**
     * when a method will be implement latter, not now, you need it.
     */
    @Pure
    public static <T> T todo() {
        throw new RuntimeException("todo");
    }
    /**
     * instanceof
     */
    @Pure
    public static boolean isInstanceOf(@Nullable Object o, @Nonnull Class clazz) {
        return o != null && clazz.isAssignableFrom(o.getClass());
    }
    /**
     * when the first is false logical, return the second value.
     */
    @Pure
    public static @Nullable
    <T> T orElse(@Nullable T t, @Nullable T elseValue) {
        return t!=null ? t : elseValue;
    }
    /**
     * just like orElse
     */
    @Pure
    public static @Nullable
    <T> T orElseGet(@Nullable T t, @Nonnull Supplier<T> supplier) {
        if (t!=null) {
            return t;
        }
        return supplier.get();
    }
    /**
     * just like orElse
     */
    @SneakyThrows
    @Pure
    public static <T, X extends Throwable> T orElseThrow(@Nullable T t,
                                                         @Nonnull Supplier<? extends X> supplier) {
        if (t!=null) {
            return t;
        }
        throw supplier.get();
    }
    /**
     * java generic type not support covariant and contravariant.
     * <p>
     * List<Animal> animals = (List<Cat>)(Object)cats;
     * ->
     * List<Animal> animals = cast(cats);
     */
    @Pure
    public static @Nullable
    <T> T cast(@Nullable Object o) {
        return (T) o;
    }
    /**
     * just like cast(Object o)
     * but sometimes, the compiler cannot deduction the type,
     * so we can point the type.
     */
    @Pure
    public static @Nullable
    <T> T cast(@Nullable Object o, @Nonnull Class<T> clazz) {
        return (T) o;
    }
    /**
     * sometimes, we need return more than one value in function.
     */
    @Pure
    public static @Nonnull Tuple0 tuple() {
        return new Tuple0();
    }
    @Pure
    public static @Nonnull
    <V1> Tuple1<V1> tuple(V1 _1) {
        return new Tuple1<>(_1);
    }
    @Pure
    public static @Nonnull
    <V1, V2> Tuple2<V1, V2> tuple(V1 _1, V2 _2) {
        return new Tuple2<>(_1, _2);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3> Tuple3<V1, V2, V3> tuple(V1 _1, V2 _2, V3 _3) {
        return new Tuple3<>(_1, _2, _3);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4> Tuple4<V1, V2, V3, V4> tuple(V1 _1, V2 _2, V3 _3, V4 _4) {
        return new Tuple4<>(_1, _2, _3, _4);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5> Tuple5<V1, V2, V3, V4, V5> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5) {
        return new Tuple5<>(_1, _2, _3, _4, _5);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5, V6> Tuple6<V1, V2, V3, V4, V5, V6> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5, V6 _6) {
        return new Tuple6<>(_1, _2, _3, _4, _5, _6);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5, V6, V7> Tuple7<V1, V2, V3, V4, V5, V6, V7> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5, V6 _6, V7 _7) {
        return new Tuple7<>(_1, _2, _3, _4, _5, _6, _7);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5, V6, V7, V8> Tuple8<V1, V2, V3, V4, V5, V6, V7, V8> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5, V6 _6, V7 _7, V8 _8) {
        return new Tuple8<>(_1, _2, _3, _4, _5, _6, _7, _8);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5, V6, V7, V8, V9> Tuple9<V1, V2, V3, V4, V5, V6, V7, V8, V9> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5, V6 _6, V7 _7, V8 _8, V9 _9) {
        return new Tuple9<>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }
    @Pure
    public static @Nonnull
    <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> Tuple10<V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> tuple(V1 _1, V2 _2, V3 _3, V4 _4, V5 _5, V6 _6, V7 _7, V8 _8, V9 _9, V10 _10) {
        return new Tuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    /**
     * think about it, we have a string content ,it contains some http headers ,
     * then we need read them into a map.
     * <p>
     * string content:
     * <p>
     * Content-Type: application/json
     * Content-Length: 1024
     * <p>
     * we can use ':' as separator, read then into a dict.
     */
    @Pure
    public static @Nonnull
    Dict<String> dict(@Nonnull String content, char separator) {
        return Dict.of(content, separator);
    }
    /**
     * see dict
     */
    @Pure
    public static @Nonnull
    Dict<String> dict(@Nonnull String content) {
        return Dict.of(content, '=');
    }
    /**
     * same as groovy truth
     *
     * https://docs.groovy-lang.org/latest/html/documentation/core-semantics.html#Groovy-Truth
     */
    public static boolean asBoolean(@Nullable Object o) {
        return org.sirenia.func.util.Objects.asBoolean(o);
    }
    @Pure
    public static boolean isArray(@Nullable Object o) {
        return o == null || o.getClass().isArray();
    }
    /**
     * deep copy a object, it must implement Serializable
     */
    @Pure
    public static @Nonnull
    <T> T copy(@Nonnull T obj) {
        byte[] bytes = SerializationUtils.serialize(obj);
        return (T) SerializationUtils.deserialize(bytes);
    }
    /**
     * like also in kotlin
     */
    @Pure
    public static @Nullable
    <T, R> R also(@Nullable T obj, @Nonnull Function<T, R> f) {
        return f.apply(obj);
    }
    /**
     * like let in kotlin.
     * we don't need builder any more.
     */
    @Pure
    public static @Nullable
    <T> T let(@Nullable T obj, @Nonnull Consumer<T> f) {
        f.accept(obj);
        return obj;
    }
    /**
     * we can new a instance without no-arg constructor.
     */
    @Pure
    public static @Nonnull
    <T> T forceNewInstance(@Nonnull Class<T> clazz) {
        return ObjenesisHelper.newInstance(clazz);
    }

    //reflect
    @Pure
    public static void doWithFields(@Nonnull Class<?> clazz, @Nonnull Consumer<Field> consumer, @Nonnull Predicate<Field> fieldPredicate) {
        ReflectionUtils.doWithFields(clazz, f -> consumer.accept(f), f -> fieldPredicate.test(f));
    }
    /***
     * spring的查找方法，ReflectionUtils#findMethod需要精确匹配参数类型。
     * 如果我根据方法参数去匹配，在方法参数中有null值的情况，是没办法匹配出来的。
     * 我们需要一个类似于groovy的metaClass#respondsTo的方法。
     */
    @Pure
    public static @Nonnull List<Method> responseTo(@Nonnull Class<?> clazz, @Nonnull String method, Object... args) {
        return Reflections.responseTo(clazz, method, args);
    }
    @SideEffect
    public static @Nullable <T> T invokeMethod(@Nonnull Method method,@Nullable Object target, Object... args) {
        ReflectionUtils.makeAccessible(method);
        return (T) ReflectionUtils.invokeMethod(method, target, args);
    }
    /**
     * 属性导航，
     * 比如 a.b.c.d.0.my.e
     * SkipJdkProxy,如果中间有jdk代理对象，则会自动对其unwrap
     * map的key，如果包含字符".",需要在path中转义为"\\."
     */
    @Pure
    public static @Nullable
    Reflections.FieldValue field(@Nonnull Object obj, @Nonnull String fieldPath) {
        return Reflections.field(obj, fieldPath);
    }
    @SideEffect
    public static @Nullable
    Reflections.FieldValue  field(@Nonnull Object obj, @Nonnull String fieldPath, boolean autoUnwrapJdkProxy) {
        return Reflections.field(obj, fieldPath, autoUnwrapJdkProxy);
    }

    @Pure
    public static @Nonnull
    String padStart(@Nonnull String str, int len, char padding) {
        return Strings.padStart(str, len, padding);
    }
    @Pure
    public static @Nonnull
    String padEnd(@Nonnull String str, int len, char padding) {
        return Strings.padStart(str, len, padding);
    }
    @Pure
    public static @Nonnull
    String repeat(@Nonnull String str, int times) {
        return Strings.repeat(str, times);
    }
    @Pure
    public static @Nonnull
    String sha256(@Nonnull CharSequence input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }
    @Pure
    public static @Nonnull
    String sha256(@Nonnull byte[] input) {
        return Hashing.sha256().hashBytes(input).toString();
    }
    @Pure
    public static @Nonnull
    String sha512(@Nonnull CharSequence input) {
        return Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString();
    }
    @Pure
    public static @Nonnull
    String sha512(@Nonnull byte[] input) {
        return Hashing.sha512().hashBytes(input).toString();
    }
    @Pure
    public static @Nonnull
    String format(@Nonnull String str, Object... args) {
        return Strings.lenientFormat(str, args);
    }
    @Pure
    public static @Nonnull
    String truncate(@Nonnull CharSequence seq, int maxLength, @Nonnull String truncationIndicator) {
        return Ascii.truncate(seq, maxLength, truncationIndicator);
    }
    @Pure
    public static @Nonnull
    String hex(byte[] buf) {
        char[] charArray = new char[buf.length * 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            charArray[j++] = HEX_CHARS[buf[i] >>> 4 & 0x0F];
            //high 4 bit, then low 4 bit
            charArray[j++] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(charArray);
    }

    @SideEffect
    public static <V> ScheduledFuture<V> setTimeout(long delayMilliseconds, @NonNull Callable<V> callable) {
        return Threads.setTimeout(delayMilliseconds,callable);
    }
    @SideEffect
    public static ScheduledFuture<?> setInterval(long initialDelayMilliseconds,
                                                 long periodMilliseconds, @NonNull Runnable command) {
        return Threads.setInterval(initialDelayMilliseconds,periodMilliseconds,command);
    }

    @SneakyThrows
    @SideEffect
    public static void zip(@Nonnull InputStream input, @Nonnull OutputStream output, @Nonnull String entryName) {
        @Cleanup ZipOutputStream zos = new ZipOutputStream(output);
        zos.putNextEntry(new ZipEntry(entryName));
        FileCopyUtils.copy(input, zos);
        zos.closeEntry();
    }
    @SneakyThrows
    @SideEffect
    public static void unZip(@Nonnull InputStream input, @Nonnull BiConsumer<String, ZipInputStream> consumer) {
        @Cleanup ZipInputStream zis = new ZipInputStream(input);
        ZipEntry entry = zis.getNextEntry();
        consumer.accept(entry.getName(), zis);
        zis.closeEntry();
    }


}
