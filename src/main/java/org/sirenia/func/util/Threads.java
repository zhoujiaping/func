package org.sirenia.func.util;

import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.core.ObjectFunc;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public abstract class Threads {
    public static final Supplier<ScheduledThreadPoolExecutor> executorSupplier =
            Suppliers.memoize(() -> ObjectFunc.let(new ScheduledThreadPoolExecutor(1, factory(),
                    new ThreadPoolExecutor.AbortPolicy() {
                    }), it -> {
                it.setMaximumPoolSize(1);
            }));
    private static ThreadFactory factory() {
        return new ThreadFactoryBuilder().setDaemon(true)
                .setNameFormat("schedule-%s")
                .setUncaughtExceptionHandler((t, e) -> log.error("", e)).build();
    }
    @SideEffect
    public static <V> ScheduledFuture<V> setTimeout(long delayMilliseconds, @NonNull Callable<V> callable) {
        ScheduledFuture<V> future = executorSupplier.get().schedule(callable, delayMilliseconds, TimeUnit.MILLISECONDS);
        return future;
    }

    @SideEffect
    public static ScheduledFuture<?> setInterval(long initialDelayMilliseconds,
                                                 long periodMilliseconds, @NonNull Runnable command) {
        ScheduledFuture<?> future = executorSupplier.get().scheduleAtFixedRate(command,
                initialDelayMilliseconds, periodMilliseconds, TimeUnit.MILLISECONDS);
        return future;
    }
}
