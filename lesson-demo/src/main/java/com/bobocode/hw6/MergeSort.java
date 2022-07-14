package com.bobocode.hw6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSort {

    public static void main(String[] args) {
        final var ints = generateList(20);
        ints.forEach(el -> System.out.print(el + " "));
        sort(ints);
        System.out.println();
        ints.forEach(el -> System.out.print(el + " "));
    }

    public static <T extends Comparable<T>> void sort(final List<T> list) {
        if (list.size() < 2) {
            return;
        }

        final int mid = list.size() / 2;
        final var left = new ArrayList<>(list.subList(0, mid));
        final var right = new ArrayList<>(list.subList(mid, list.size()));

        sort(left);
        sort(right);

        merge(list, left, right);
    }

    private static <T extends Comparable<T>> void merge(final List<T> list,
                                                        final List<T> left,
                                                        final List<T> right) {
        int l = 0, r = 0, i = 0;
        while (l < left.size() && r < right.size()) {
            if (left.get(l).compareTo(right.get(r)) <= 0) {
                list.set(i++, left.get(l++));
            } else {
                list.set(i++, right.get(r++));
            }
        }

        while (l < left.size()) {
            list.set(i++, left.get(l++));
        }

        while (r < right.size()) {
            list.set(i++, right.get(r++));
        }
    }

    private static List<Integer> generateList(final int n) {
        var list = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            list.add(ThreadLocalRandom.current().nextInt(n));
        }

        return list;
    }
}
