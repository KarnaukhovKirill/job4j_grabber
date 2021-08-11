package ru.job4j.grabber.store;

import ru.job4j.grabber.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        cnn.close();
    }

    @Override
    public void save(Post post) {
        try (var statement = cnn.prepareStatement("insert into post(name, text, link, created) "
                                                                                            + "values (?, ?, ?, ?)",
                                                                                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            var created = Timestamp.valueOf(post.getCreated());
            statement.setTimestamp(4, created);
            statement.execute();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                post.setId(keys.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (var statement = cnn.prepareStatement("select * from post")) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("link"),
                            resultSet.getString("text"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (var statement = cnn.prepareStatement("select * from post where id = ?")) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                post = new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getString("text"),
                        resultSet.getTimestamp("created").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
}
