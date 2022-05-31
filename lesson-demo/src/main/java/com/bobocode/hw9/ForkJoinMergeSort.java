package com.bobocode.hw9;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ForkJoinMergeSort {

    @SneakyThrows
    public static void main(String[] args) {
        final var arr = generateArray(40);
        System.out.println(Arrays.toString(arr));

        ForkJoinPool.commonPool().invoke(new MergeSortTask(arr));
        System.out.println(Arrays.toString(arr));

    }

    @RequiredArgsConstructor
    private static class MergeSortTask extends RecursiveAction {

        private final int[] arr;

        @Override
        protected void compute() {
            sort(arr);
        }

        private void sort(final int[] arr) {
            if (arr.length < 2) {
                return;
            }

            var mid = arr.length / 2;
            var left = new int[mid];
            var right = new int[arr.length - mid];
            System.arraycopy(arr, 0, left, 0, mid);
            System.arraycopy(arr, mid, right, 0, arr.length - mid);

            var leftTask = new MergeSortTask(left);
            var rightTask = new MergeSortTask(right);

            leftTask.fork();
            rightTask.compute();
            leftTask.join();

            merge(arr, left, right);
        }

        private void merge(final int[] arr, final int[] left, final int[] right) {
            int i = 0, l = 0, r = 0;
            while (l < left.length && r < right.length) {
                if (left[l] <= right[r]) {
                    arr[i++] = left[l++];
                } else {
                    arr[i++] = right[r++];
                }
            }

            System.arraycopy(left, l, arr, i, left.length - l);
            System.arraycopy(right, r, arr, i, right.length - r);
        }
    }

    public static int[] generateArray(final int n) {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(n * 10))
                .limit(n)
                .toArray();
    }
}
