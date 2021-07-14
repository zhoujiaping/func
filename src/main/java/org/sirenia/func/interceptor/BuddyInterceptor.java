package org.sirenia.func.interceptor;

import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import org.apache.commons.lang3.mutable.MutableObject;
import org.sirenia.func.util.Methods;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * intercept class
 */
@Slf4j
public abstract class BuddyInterceptor {
    //public static final String SUPERCLASS_NAME_METHOD_NAME = "_15f078f4_2f32_413d_93b8_30251c9219f7";

    /**
     * className->handler
     */
    static final Map<String, ProxyHandler> handlers = new ConcurrentHashMap<>();

    public static @Nonnull
    <T> Class<? extends T> proxy(@Nonnull Class<T> targetClass, @Nonnull ProxyHandler handler) {
        return proxy(targetClass,null,handler);
    }

    public static @Nonnull
    <T> Class<? extends T> proxy(@Nonnull Class<T> targetClass, @Nullable Predicate<Method> filter, @Nonnull ProxyHandler handler) {
        Objects.requireNonNull(targetClass, "targetClass can't be null");
        if (ClassUtils.isPrimitiveOrWrapper(targetClass)) {
            throw new RuntimeException("can't proxy a primitive or wrapper class");
        }
        List<String> proxyMethods = new LinkedList<>();
        if(filter == null){
            filter = method->!Methods.isObjectDefaultOrOverride(method);
        }else{
            Predicate<Method> finalFilter1 = filter;
            filter = method->!Methods.isObjectDefaultOrOverride(method) && finalFilter1.test(method);
        }
        DynamicType.Builder<T> builder = new ByteBuddy().subclass(targetClass);
        MutableObject<DynamicType.Builder<T>> builderHolder = new MutableObject<>(builder);
        Predicate<Method> finalFilter = filter;
        ReflectionUtils.doWithMethods(targetClass, method -> {
                    builderHolder.setValue(
                            builderHolder.getValue().define(method).intercept(MethodDelegation.to(BuddyInterceptor.class))
                    );
                    proxyMethods.add(method.getName());
                },
                method -> finalFilter.test(method));
        log.debug("proxyMethods={}", proxyMethods);
        Class<? extends T> generated = builderHolder.getValue()/*.defineMethod(SUPERCLASS_NAME_METHOD_NAME, String.class,
                Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL).intercept(
                FixedValue.value(targetClass.getName()))*/.make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        BuddyInterceptor.addHandler(targetClass.getName(), handler);
        return generated;
    }

    @Synchronized
    public static ProxyHandler addHandler(String className, ProxyHandler handler) {
        return handlers.put(className, handler);
    }

    @SneakyThrows
    @RuntimeType
    public static Object intercept(@AllArguments Object[] args,
                                   @This(optional = true) Object target,
                                   @Origin Method method,
                                   @Origin Class<?> origClass,
                                   @SuperMethod(nullIfImpossible=true) Method superMethod) {
        //if it's static method, the superMethod will be null. we need find it's superMethod
        if(superMethod==null){
            superMethod = ReflectionUtils.findMethod(origClass,method.getName(),method.getParameterTypes());
        }
        return handlers.get(origClass.getName()).invoke(target, method, superMethod,args);
    }

}
