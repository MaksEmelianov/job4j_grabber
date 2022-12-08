package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    public static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PAGE_GET = "?page=";
    public static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);
    public static final int PAGE_COUNT = 1;
    public static final int LIMIT = 1;
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private static String retrieveDescription(String link) throws IOException {
        return Jsoup.connect(link)
                .get()
                .select(".vacancy-description__text")
                .first()
                .text();
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= PAGE_COUNT; i++) {
            Connection connection = Jsoup.connect(String.format("%s%s%s", link, PAGE_GET, i));
            Document document = connection.get();
            Elements elsFromOnePage = document.select(".vacancy-card__inner");
            elsFromOnePage.stream()
                    .limit(LIMIT)
                    .forEach(row -> addDataInList(row, posts));
        }
        return posts;
    }

    private void addDataInList(Element row, List<Post> posts) {
        Element linkElement = row.select(".vacancy-card__title")
                .first()
                .child(0);
        String title = linkElement.text();
        LocalDateTime date = dateTimeParser.parse(row.select(".vacancy-card__date")
                .first()
                .child(0)
                .attr("datetime"));
        String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        String desc = null;
        try {
            desc = retrieveDescription(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        posts.add(new Post(title, link, desc, date));
    }
}
