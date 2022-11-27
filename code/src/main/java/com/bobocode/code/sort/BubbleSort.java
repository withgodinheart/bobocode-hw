package com.bobocode.code.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class BubbleSort {

    public static void main(String[] args) {
        var arr = generateArray(50);
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // 11, 5, 7, 8, 3 /i=0
    // 5, 7, 8, 3, 11 /i=1
    // 5, 7, 3, 8, 11 /i=2
    private static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    var tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    private static int[] generateArray(int n) {
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(n * 10);
        }

        return arr;
    }
}
