package ru.job4j.grabber.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

public class SqlRuDateTimeParser implements DateTimeParses {
    private static final Calendar TODAY = Calendar.getInstance();
    private static final Map<String, String> MONTHS = new HashMap<>();
    static {
        MONTHS.put("янв", "янв.");
        MONTHS.put("фев", "фев.");
        MONTHS.put("мар", "мар.");
        MONTHS.put("апр", "апр.");
        MONTHS.put("май", "мая");
        MONTHS.put("июн", "июн.");
        MONTHS.put("июл", "июл.");
        MONTHS.put("авг", "авг.");
        MONTHS.put("сен", "сен.");
        MONTHS.put("окт", "окт.");
        MONTHS.put("ноя", "ноя.");
        MONTHS.put("дек", "дек.");
    }
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd MMM yy, HH:mm");

    @Override
    public LocalDateTime parse(String parse) {
        if (parse.contains("сегодня")) {
            parse = replaceTodayOrYesterday(parse, sdf -> sdf.format(TODAY.getTime()));
            return getLocalDateTime(parse);
        }
        if (parse.contains("вчера")) {
            TODAY.add(Calendar.DAY_OF_YEAR, -1);
            var yesterday = TODAY.getTime();
            parse = replaceTodayOrYesterday(parse, sdf -> sdf.format(yesterday));
            return getLocalDateTime(parse);
        }
        parse = changeMonth(parse);
        return getLocalDateTime(parse);

    }

    private String changeMonth(String parse) {
        var array = parse.split(" ");
        array[1] = MONTHS.get(array[1]);
        return String.join(" ", array);
    }

    private LocalDateTime getLocalDateTime(String parse) {
        LocalDateTime localDateTime = null;
        try {
            var date = FORMATTER.parse(parse);
            localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    public String replaceTodayOrYesterday(String parse, Function<SimpleDateFormat, String> function) {
        var todayDate = function.apply(FORMATTER);
        var time = parse.split(", ")[1];
        var date = todayDate.split(", ")[0];
        return date + ", " + time;
    }
}
