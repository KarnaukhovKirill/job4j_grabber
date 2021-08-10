package ru.job4j.grabber;

import org.jsoup.Jsoup;
import ru.job4j.grabber.utils.DateTimeParses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final DateTimeParses dateTimeParses;

    public SqlRuParse(DateTimeParses dateTimeParses) {
        this.dateTimeParses = dateTimeParses;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            for (int i = 1; i <= 5; i++) {
                var page = String.format("%s/%d", link, i);
                var doc = Jsoup.connect(page).get();
                var row = doc.select(".postslisttopic");
                for (int j = 3; j < row.size(); j++) {
                    var td = row.get(j);
                    var href = td.child(0);
                    var postUrl = href.attr("href");
                    var post = detail(postUrl);
                    posts.add(post);
                }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        try {
            var doc = Jsoup.connect(link).get();
            var msgBody = doc.select(".msgBody").get(1);
            var description = msgBody.text();
            var msgFooter = doc.select("td.msgFooter").get(0);
            var time = msgFooter.text().split(" \\[")[0];
            var date = dateTimeParses.parse(time);
            var header = doc.select("td.messageHeader").text();
            var title = header.split(" \\[new]")[0];
            return new Post(title, link, description, date);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
