package ru.job4j;

import ru.job4j.grabber.SqlRuParse;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

public class Main {
    public static void main(String[] args) {
        SqlRuParse ruParse = new SqlRuParse(new SqlRuDateTimeParser());
        var list = ruParse.list("https://www.sql.ru/forum/job-offers");
        list.forEach(System.out::println);
    }
}
