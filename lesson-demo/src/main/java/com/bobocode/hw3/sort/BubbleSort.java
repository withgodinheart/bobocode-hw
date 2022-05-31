package com.bobocode.hw3.sort;

import static com.bobocode.hw3.sort.Util.checkTimeDynamically;

public class BubbleSort {

    public static void main(String[] args) {
        checkTimeDynamically(BubbleSort::sort, 5, 1_000, 101_000, 5_000);
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    var temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
