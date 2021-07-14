package org.sirenia.func.core;

import org.sirenia.func.anno.Pure;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public abstract class ArrayFunc {

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
}
