package org.sirenia.func.core;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.sirenia.func.anno.SideEffect;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class ZipFunc {
    @SneakyThrows
    @SideEffect
    public static void zip(@Nonnull InputStream input, @Nonnull OutputStream output, @Nonnull String entryName) {
        @Cleanup ZipOutputStream zos = new ZipOutputStream(output);
        zos.putNextEntry(new ZipEntry(entryName));
        FileCopyUtils.copy(input, zos);
        zos.closeEntry();
    }

    @SneakyThrows
    @SideEffect
    public static void unZip(@Nonnull InputStream input, @Nonnull BiConsumer<String, ZipInputStream> consumer) {
        @Cleanup ZipInputStream zis = new ZipInputStream(input);
        ZipEntry entry = zis.getNextEntry();
        consumer.accept(entry.getName(), zis);
        zis.closeEntry();
    }
}
