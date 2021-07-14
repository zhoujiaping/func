package org.sirenia.func.core;

import org.sirenia.func.anno.Pure;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.util.Reflections;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ReflectFunc {

    //reflect
    @Pure
    public static void doWithFields(@Nonnull Class<?> clazz, @Nonnull Consumer<Field> consumer, @Nonnull Predicate<Field> fieldPredicate) {
        ReflectionUtils.doWithFields(clazz, f -> consumer.accept(f), f -> fieldPredicate.test(f));
    }

    /***
     * spring ReflectionUtils#findMethod need exactly parameter types.
     * we need a method like metaClass#respondsTo in groovy
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
     * get/set field value, use a fieldPath
     * eg: fieldPath="customer.order[0].itemMap['apple'].price"
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


}
