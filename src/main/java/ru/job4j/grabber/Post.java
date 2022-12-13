package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Post {

    private int id;
    private final String link;
    private final String title;
    private final String description;
    private final LocalDateTime created;

    public Post(String link, String title, String description, LocalDateTime created) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.created = created;
    }

    public Post(int id, String link, String title, String description, LocalDateTime created) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.created = created;
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

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
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
                && link.equals(post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link);
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
