package org.sirenia.func.core;

import lombok.NonNull;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.util.Threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

public abstract class ThreadFunc {

    @SideEffect
    public static <V> ScheduledFuture<V> setTimeout(long delayMilliseconds, @NonNull Callable<V> callable) {
        return Threads.setTimeout(delayMilliseconds,callable);
    }

    @SideEffect
    public static ScheduledFuture<?> setInterval(long initialDelayMilliseconds,
                                                 long periodMilliseconds, @NonNull Runnable command) {
        return Threads.setInterval(initialDelayMilliseconds,periodMilliseconds,command);
    }

}
