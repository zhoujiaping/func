package org.sirenia.func.core;

import com.google.common.base.Suppliers;
import lombok.SneakyThrows;
import org.sirenia.func.anno.Pure;
import org.sirenia.func.util.Functions;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class FunctionFunc {

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
     * in java, the functional public abstract class is not declare with exception.
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

}
