package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post {" + System.lineSeparator() + "ID: " + id + System.lineSeparator()
        + "Title: " + title + System.lineSeparator()
        + "Link: " + link + System.lineSeparator()
        + "Description: " + description + System.lineSeparator()
        + "Created at " + created + System.lineSeparator()
        + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result *= prime * id;
        result *= prime * link.hashCode();
        return result * prime * title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Post post = (Post) obj;
        return this.id == post.id
                && this.link.equals(post.link)
                && this.title.equals(post.title);
    }
}
