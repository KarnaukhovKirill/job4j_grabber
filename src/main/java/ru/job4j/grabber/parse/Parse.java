package ru.job4j.grabber.parse;

import ru.job4j.grabber.models.Post;

import java.util.List;

public interface Parse {
    List<Post> list(String link);
    Post detail(String link);
}
