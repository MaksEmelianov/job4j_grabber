package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HabrCareerParse {

    public static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PAGE_GET = "?page=";
    public static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);
    public static final int PAGE_COUNT = 1;
    public static final int V_COUNT = 1;
    public static int countVacancy = 1;

    public static void main(String[] args) throws IOException {
        List<Elements> rows = new ArrayList<>();
        for (int i = 1; i <= PAGE_COUNT; i++) {
            Connection connection = Jsoup.connect(String.format("%s%s%s", PAGE_LINK, PAGE_GET, i));
            Document document = connection.get();
            rows.add(document.select(".vacancy-card__inner"));
        }
        rows.stream()
                .flatMap(Collection::stream)
                .limit(V_COUNT)
                .forEach(HabrCareerParse::parseDataFromPage);
    }

    private static void parseDataFromPage(Element row) {
        Element titleElement = row.select(".vacancy-card__title").first();
        Element linkElement = titleElement.child(0);
        String vacancyName = titleElement.text();
        String fullDate = row.select(".vacancy-card__date")
                .first()
                .child(0)
                .attr("datetime");
        String datetime = new HabrCareerDateTimeParser().parse(fullDate).toString();
        String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        try {
            System.out.printf("%s - %s [%s] %s%n%s",
                    countVacancy, vacancyName, datetime, link, retrieveDescription(link));
        } catch (IOException e) {
            e.printStackTrace();
        }
        countVacancy++;
    }

    private static String retrieveDescription(String link) throws IOException {
        return Jsoup.connect(link)
                .get()
                .select(".vacancy-description__text")
                .first()
                .text();
    }
}
