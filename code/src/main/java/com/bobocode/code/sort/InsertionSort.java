package com.bobocode.code.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class InsertionSort {

    public static void main(String[] args) {
        var arr = generateArray(50);
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            var picked = arr[i];
            var currentPosition = i;
            while (currentPosition > 0 && arr[currentPosition - 1] > picked) {
                arr[currentPosition] = arr[currentPosition - 1];
                currentPosition--;
            }
            arr[currentPosition] = picked;
        }
    }

    private static int[] generateArray(final int n) {
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(n);
        }

        return arr;
    }
}
