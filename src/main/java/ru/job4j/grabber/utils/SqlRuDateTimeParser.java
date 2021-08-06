package ru.job4j.grabber.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

public class SqlRuDateTimeParser implements DateTimeParses {
    Calendar today = Calendar.getInstance();
    Map<String, String> months = new HashMap<>();
    {
        months.put("янв", "янв.");
        months.put("фев", "фев.");
        months.put("мар", "мар.");
        months.put("апр", "апр.");
        months.put("май", "май.");
        months.put("июн", "июн.");
        months.put("июл", "июл.");
        months.put("авг", "авг.");
        months.put("сен", "сен.");
        months.put("окт", "окт.");
        months.put("ноя", "ноя.");
        months.put("дек", "дек.");
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy, HH:mm");

    @Override
    public LocalDateTime parse(String parse) {
        if (parse.contains("сегодня")) {
            parse = replaceTodayOrYesterday(parse, sdf -> sdf.format(today.getTime()));
            return getLocalDateTime(parse);
        }
        if (parse.contains("вчера")) {
            today.add(Calendar.DAY_OF_YEAR, -1);
            var yesterday = today.getTime();
            parse = replaceTodayOrYesterday(parse, sdf -> sdf.format(yesterday));
            return getLocalDateTime(parse);
        }
        parse = changeMonth(parse);
        return getLocalDateTime(parse);

    }

    private String changeMonth(String parse) {
        var array = parse.split(" ");
        array[1] = months.get(array[1]);
        return String.join(" ", array);
    }

    private LocalDateTime getLocalDateTime(String parse) {
        LocalDateTime localDateTime = null;
        try {
            var date = formatter.parse(parse);
            localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    public String replaceTodayOrYesterday(String parse, Function<SimpleDateFormat, String> function) {
        var todayDate = function.apply(formatter);
        var time = parse.split(", ")[1];
        var date = todayDate.split(", ")[0];
        return date + ", " + time;
    }
}
