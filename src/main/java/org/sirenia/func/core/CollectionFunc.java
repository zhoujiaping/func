package org.sirenia.func.core;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.mutable.MutableInt;
import org.sirenia.func.anno.Pure;
import org.sirenia.func.tuple.Tuple2;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.sirenia.func.core.ObjectFunc.cast;
import static org.sirenia.func.core.ObjectFunc.isInstanceOf;


public abstract class CollectionFunc {

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
        Map<K, V> map = new HashMap<>();
        if (origMap != null) {
            map.putAll(origMap);
        }
        return map;
    }

    @Pure
    public static @Nonnull <K,V> TreeMap<K,V> treeMapOf(@Nullable Map<K, V> origMap){
        TreeMap<K, V> map = new TreeMap<>();
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
    public static @Nullable
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
}
