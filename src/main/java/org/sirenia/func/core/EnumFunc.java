package org.sirenia.func.core;

import org.sirenia.func.anno.Pure;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class EnumFunc {

    @Pure
    public static @Nonnull <E extends Enum<E>> Map<String,E> enumMap(@Nonnull Class<E> enumClass) {
        final Map<String, E> map = new LinkedHashMap<>();
        for (final E e: enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

}
