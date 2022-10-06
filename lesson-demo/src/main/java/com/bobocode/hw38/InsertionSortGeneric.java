package com.bobocode.hw38;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InsertionSortGeneric {

    public static void main(String[] args) {
        var integers = List.of(1, 4, 3, 1, 9, 2, 3, 4, 5, 6, 0, 1, 2, 3, 4);
        var result = sort(integers, Comparator.comparingInt(Integer::intValue));

        System.out.println(result);
    }

    public static <T> List<T> sort(List<T> list, Comparator<? super T> cmp) {
        list = new ArrayList<>(list);

        for (var i = 1; i < list.size(); i++) {
            var picked = list.get(i);
            var currentPosition = i;

            while (currentPosition > 0
                    && cmp.compare(picked, list.get(currentPosition - 1)) < 0) {
                list.set(currentPosition, list.get(currentPosition - 1));
                currentPosition--;
            }

            list.set(currentPosition, picked);
        }

        return list;
    }
}
