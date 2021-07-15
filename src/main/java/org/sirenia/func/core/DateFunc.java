package org.sirenia.func.core;

import org.sirenia.func.anno.Pure;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class DateFunc {

    public static final Map<String,DateTimeFormatter> formatters = new ConcurrentHashMap<>();

    private static DateTimeFormatter ofPattern(String pattern){
        DateTimeFormatter fmt = formatters.get(pattern);
        if(fmt == null){
            fmt = DateTimeFormatter.ofPattern(pattern);
            formatters.put(pattern,fmt);
        }
        return fmt;
    }
    //format
    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDate date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDate date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull LocalDateTime date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull LocalTime date, @Nonnull String pattern) {
        return date.format(ofPattern(pattern));
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull LocalTime date) {
        return DateTimeFormatter.ISO_LOCAL_TIME.format(date);
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull Date date) {
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        //return format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Pure
    public static @Nonnull
    String format(@Nonnull Date date, @Nonnull String pattern) {
        return date.toInstant().atZone(ZoneId.systemDefault()).format(ofPattern(pattern));
    }

    @Pure
    //toLocalDate
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Pure
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull String date, @Nonnull String pattern) {
        return LocalDate.parse(date, ofPattern(pattern));
    }

    @Pure
    public static @Nonnull
    LocalDate toLocalDate(@Nonnull Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //toLocalDateTime
    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull String date, @Nonnull String pattern) {
        return LocalDateTime.parse(date, ofPattern(pattern));
    }

    @Pure
    public static @Nonnull
    LocalDateTime toLocalDateTime(@Nonnull Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    //toDate
    @Pure
    public static @Nonnull
    Date toDate(@Nonnull String date) {
        return toDate(toLocalDateTime(date));
    }

    @Pure
    public static @Nonnull
    Date toDate(@Nonnull String date, @Nonnull String pattern) {
        return toDate(toLocalDateTime(date, pattern));
    }

    @Pure
    public static @Nonnull
    Date toDate(@Nonnull LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Pure
    public static @Nonnull
    Date toDate(@Nonnull LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
