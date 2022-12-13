package ru.job4j.kiss;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxMinTest {

    @Test
    void minTest() {
        List<Integer> list = List.of(1, 0, 2, 3, 4, 5);
        assertThat(new MaxMin().min(list, Integer::compareTo)).isEqualTo(0);
    }

    @Test
    void maxTest() {
        List<Integer> list = List.of(1, 0, 2, 3, 4, 5);
        assertThat(new MaxMin().max(list, Integer::compareTo)).isEqualTo(5);
    }
}
