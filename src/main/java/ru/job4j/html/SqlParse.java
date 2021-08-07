package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.format.DateTimeFormatter;

public class SqlParse {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
    public static void main(String[] args) throws IOException {
        for (int j = 1; j <= 5; j++) {
            SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
            var page = String.format("https://www.sql.ru/forum/job-offers/%d", j);
            Document doc = Jsoup.connect(page).get();
            Elements row = doc.select(".postslisttopic");
            Elements rowDate = doc.select("td.altCol[style]");
            for (int i = 3; i < row.size(); i++) {
                Element td = row.get(i);
                Element href = td.child(0);
                //Element date = rowDate.get(i);
                parseJobPage(href);
                //System.out.println(href.attr("href"));
                //System.out.println(href.text());
                //System.out.println(parser.parse(date.text()).format(FORMATTER));
                //System.out.println();
            }
            System.out.printf("КОНЕЦ СТРАНИЦЫ %d", j);
            System.out.println();
        }
    }

    private static void parseJobPage(Element href) throws IOException {
        Document doc = Jsoup.connect(href.attr("href")).get();
        var msgBody = doc.select(".msgBody").get(1);
        var description = msgBody.text();
        System.out.println(description);
        var msgFooter = doc.select("td.msgFooter").get(0);
        var time = msgFooter.text().split(" \\[")[0];
        System.out.println(time);
    }
}
