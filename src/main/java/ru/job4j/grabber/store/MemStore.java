package ru.job4j.grabber.store;

import ru.job4j.grabber.Post;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {

    List<Post> posts = new ArrayList<>();

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post findById(int id) {
        return posts.get(id - 1);
    }

    @Override
    public void close() {
        if (!posts.isEmpty()) {
            posts.clear();
        }
    }
}
