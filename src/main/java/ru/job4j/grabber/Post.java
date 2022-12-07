package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Post {

    private final int id;
    private final String title;
    private final String link;
    private final String description;
    private final LocalDateTime created;

    public Post(int id, String title, String link, String description, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && link.equals(post.link)
                && description.equals(post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("link='" + link + "'")
                .add("description='" + description + "'")
                .add("created=" + created)
                .toString();
    }
}
