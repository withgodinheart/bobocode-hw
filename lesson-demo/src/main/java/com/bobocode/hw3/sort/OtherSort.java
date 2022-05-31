package com.bobocode.hw3.sort;

import static com.bobocode.hw3.sort.Util.checkTimeDynamically;

public class OtherSort {

    public static void main(String[] args) {
        checkTimeDynamically(OtherSort::sort, 5, 1_000, 101_000, 5_000);
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    var temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
