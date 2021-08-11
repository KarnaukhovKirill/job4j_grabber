package ru.job4j;

import ru.job4j.grabber.models.Post;
import ru.job4j.grabber.store.PsqlStore;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try (var in = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties")) {
            var config = new Properties();
            config.load(in);
            PsqlStore store = new PsqlStore(config);
            Post post1 = new Post(
                    "first post",
                    "Junior за 300к/сек",
                    "www.url.ru",
                    LocalDateTime.now());
            Post post2 = new Post(
                    "second post",
                    "Middle за 500к/сек",
                    "www.url01.ru",
                    LocalDateTime.now().minus(10, ChronoUnit.HOURS));
            store.save(post1);
            store.save(post2);
            store.getAll().forEach(System.out::println);
            System.out.println(store.findById(post2.getId()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
