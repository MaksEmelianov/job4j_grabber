package ru.job4j.grabber.parses;

import ru.job4j.grabber.Post;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface Parse {
    List<Post> list(String link) throws IOException;
}
