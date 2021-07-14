package org.sirenia.func.core;

import com.google.common.base.Ascii;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import org.sirenia.func.anno.Pure;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public abstract class TextFunc {

    public static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    @Pure
    public static @Nonnull
    String padStart(@Nonnull String str, int len, char padding) {
        return Strings.padStart(str, len, padding);
    }

    @Pure
    public static @Nonnull
    String padEnd(@Nonnull String str, int len, char padding) {
        return Strings.padStart(str, len, padding);
    }

    @Pure
    public static @Nonnull
    String repeat(@Nonnull String str, int times) {
        return Strings.repeat(str, times);
    }

    @Pure
    public static @Nonnull
    String sha256(@Nonnull CharSequence input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    @Pure
    public static @Nonnull
    String sha256(@Nonnull byte[] input) {
        return Hashing.sha256().hashBytes(input).toString();
    }

    @Pure
    public static @Nonnull
    String sha512(@Nonnull CharSequence input) {
        return Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString();
    }

    @Pure
    public static @Nonnull
    String sha512(@Nonnull byte[] input) {
        return Hashing.sha512().hashBytes(input).toString();
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull String str, Object... args) {
        return Strings.lenientFormat(str, args);
    }

    @Pure
    public static @Nonnull
    String truncate(@Nonnull CharSequence seq, int maxLength, @Nonnull String truncationIndicator) {
        return Ascii.truncate(seq, maxLength, truncationIndicator);
    }

    @Pure
    public static @Nonnull
    String hex(byte[] buf) {
        char[] charArray = new char[buf.length * 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            charArray[j++] = HEX_CHARS[buf[i] >>> 4 & 0x0F];
            //high 4 bit, then low 4 bit
            charArray[j++] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(charArray);
    }
}
