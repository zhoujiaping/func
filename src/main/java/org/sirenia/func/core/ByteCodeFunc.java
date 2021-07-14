package org.sirenia.func.core;

import lombok.SneakyThrows;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.interceptor.BuddyInterceptor;
import org.sirenia.func.interceptor.ProxyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public abstract class ByteCodeFunc {

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
}
