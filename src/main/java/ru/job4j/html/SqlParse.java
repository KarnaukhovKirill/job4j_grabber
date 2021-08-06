package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlParse {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements rowDate = doc.select("td.altCol[style]");
        for (int i = 0; i < row.size(); i++) {
            Element td = row.get(i);
            Element href = td.child(0);
            Element date = rowDate.get(i);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(date.text());
            System.out.println();
        }
    }
}
