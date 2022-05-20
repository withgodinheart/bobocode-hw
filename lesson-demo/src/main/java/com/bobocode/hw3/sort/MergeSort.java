package com.bobocode.hw3.sort;

import static com.bobocode.hw3.sort.Util.checkTimeDynamically;

public class MergeSort {

    public static void main(String[] args) {
        checkTimeDynamically(MergeSort::sort, 5, 1_000, 101_000, 5_000);
    }

    private static void sort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        final var arrMid = arr.length / 2;
        final int[] leftArr = new int[arrMid];
        final int[] rightArr = new int[arr.length - arrMid];

        System.arraycopy(arr, 0, leftArr, 0, arrMid);
        System.arraycopy(arr, arrMid, rightArr, 0, arr.length - arrMid);

        sort(leftArr);
        sort(rightArr);

        merge(arr, leftArr, rightArr);
    }

    private static void merge(int[] arr, int[] leftArr, int[] rightArr) {
        int l = 0, r = 0, i = 0;
        final int leftArrSize = leftArr.length;
        final int rightArrSize = rightArr.length;

        while (l < leftArrSize && r < rightArrSize) {
            if (leftArr[l] <= rightArr[r]) {
                arr[i++] = leftArr[l++];
            } else {
                arr[i++] = rightArr[r++];
            }
        }

        System.arraycopy(leftArr, l, arr, i, leftArrSize - l);
        System.arraycopy(rightArr, r, arr, i, rightArrSize - r);

//        while (l < leftArrSize) {
//            arr[i++] = leftArr[l++];
//        }
//        while (r < rightArrSize) {
//            arr[i++] = rightArr[r++];
//        }
    }
}
