package ru.job4j.grabber.store;

import ru.job4j.grabber.models.Post;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {
    private final List<Post> items = new ArrayList<>();
    private int ids = 1;

    @Override
    public void save(Post post) {
        post.setId(ids++);
        items.add(post);
    }

    @Override
    public List<Post> getAll() {
        return this.items;
    }

    @Override
    public Post findById(int id) {
        var index = indexOf(id);
        return items.get(index);
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId() == id) {
                return index;
            }
        }
        return rsl;
    }
}
