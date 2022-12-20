package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;

public class MaxMin {

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return findElement(value, ((t1, t2) -> comparator.compare(t1, t2) < 0));
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return findElement(value, ((t1, t2) -> comparator.compare(t1, t2) > 0));
    }

    private <T> T findElement(List<T> value, BiPredicate<T, T> biPredicate) {
        if (value.isEmpty()) {
            return null;
        }
        T rsl = value.get(0);
        for (T t : value) {
            if (biPredicate.test(rsl, t)) {
                rsl = t;
            }
        }
        return rsl;
    }
}
