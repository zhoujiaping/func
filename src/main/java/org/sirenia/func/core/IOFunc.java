package org.sirenia.func.core;

import lombok.SneakyThrows;
import org.sirenia.func.anno.SideEffect;
import org.sirenia.func.util.IOs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.sirenia.func.core.TextFunc.format;

public abstract class IOFunc {
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

}
