package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SqlParse {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
    public static void main(String[] args) throws IOException {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements rowDate = doc.select("td.altCol[style]");
        for (int i = 0; i < row.size(); i++) {
            Element td = row.get(i);
            Element href = td.child(0);
            Element date = rowDate.get(i);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(parser.parse(date.text()).format(dtf)); //вывод изменённой даты в консоль
            System.out.println();
        }
    }
}
