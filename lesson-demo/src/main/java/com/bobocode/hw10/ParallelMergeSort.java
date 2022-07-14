package com.bobocode.hw10;

import com.bobocode.hw6.MergeSort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelMergeSort {

    public static void main(String[] args) {
        final var list = generateList(200_000);

        var start = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(new SortRecursiveAction<>(list));
        var executionTime = System.currentTimeMillis() - start;
        System.out.println(executionTime);

        var start1 = System.currentTimeMillis();
        MergeSort.sort(list);
        var executionTime1 = System.currentTimeMillis() - start1;
        System.out.println(executionTime1);
    }

    @RequiredArgsConstructor
    public static class SortRecursiveAction<T extends Comparable<? super T>> extends RecursiveAction {

        private final List<T> list;

        @Override
        protected void compute() {
            sort(list);
        }

        private void sort(final List<T> list) {
            if (list.size() < 2) {
                return;
            }

            final var mid = list.size() / 2;
            final var left = new ArrayList<>(list.subList(0, mid));
            final var right = new ArrayList<>(list.subList(mid, list.size()));

            final var leftAction = new SortRecursiveAction<T>(left);
            final var rightAction = new SortRecursiveAction<T>(right);

            leftAction.fork();
            rightAction.compute();
            leftAction.join();

            merge(list, left, right);
        }

        private void merge(final List<T> list,
                           final List<T> left,
                           final List<T> right) {
            int i = 0, l = 0, r = 0;
            while (l < left.size() && r < right.size()) {
                if (left.get(l).compareTo(right.get(r)) < 1) {
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
    }

    private static List<Integer> generateList(final int n) {
        var list = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            list.add(ThreadLocalRandom.current().nextInt(n * 10));
        }

        return list;
    }
}
