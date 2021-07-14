package org.sirenia.func.core;

import lombok.SneakyThrows;
import org.sirenia.func.anno.Pure;
import org.sirenia.func.dict.Dict;
import org.sirenia.func.tuple.Tuple0;
import org.sirenia.func.tuple.Tuple1;
import org.sirenia.func.tuple.Tuple10;
import org.sirenia.func.tuple.Tuple2;
import org.sirenia.func.tuple.Tuple3;
import org.sirenia.func.tuple.Tuple4;
import org.sirenia.func.tuple.Tuple5;
import org.sirenia.func.tuple.Tuple6;
import org.sirenia.func.tuple.Tuple7;
import org.sirenia.func.tuple.Tuple8;
import org.sirenia.func.tuple.Tuple9;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.util.SerializationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ObjectFunc {
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

}
