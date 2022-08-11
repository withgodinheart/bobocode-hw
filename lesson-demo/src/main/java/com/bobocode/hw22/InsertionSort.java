package com.bobocode.hw22;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InsertionSort {

    public static void main(String[] args) {
        var ints = Arrays.asList(1, 3, 6, 8, 9, 10, 3, -44, 100, 44, 1, 2);
        sort(ints, Integer::compareTo);
        System.out.println(ints);

    }

    private static <T> void sort(List<T> list, Comparator<? super T> cmp) {

        for (int i = 1; i < list.size(); i++) {
            var picked = list.get(i);
            var currentPosition = i;

            while (currentPosition > 0 &&
                    (cmp.compare(picked, list.get(currentPosition - 1)) < 0)) {
                list.set(currentPosition, list.get(currentPosition - 1));
                currentPosition--;
            }

            list.set(currentPosition, picked);
        }
    }
}
