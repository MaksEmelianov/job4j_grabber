package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin {

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return findElement(value, t -> comparator.compare(value.get(0), t) > 0);
    }

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return findElement(value, t -> comparator.compare(value.get(0), t) < 0);
    }

    public <T> T findElement(List<T> value, Predicate<T> predicate) {
        T result = null;
        for (T el : value) {
            if (predicate.test(el)) {
                result = el;
            }
        }
        return result;
    }
}
