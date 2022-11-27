package com.bobocode.code.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSort {

    public static void main(String[] args) {
        var arr = generateArray(100);
        sort(arr);

        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        var mid = arr.length / 2;
        var leftArr = new int[mid];
        var rightArr = new int[arr.length - mid];

        System.arraycopy(arr, 0, leftArr, 0, mid);
        System.arraycopy(arr, mid, rightArr, 0, arr.length - mid);

        sort(leftArr);
        sort(rightArr);

        merge(arr, leftArr, rightArr);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
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

    private static int[] generateArray(int n) {
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(n);
        }

        return arr;
    }
}
