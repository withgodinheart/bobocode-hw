package com.bobocode.hw3.sort;

import static com.bobocode.hw3.sort.Util.checkTimeDynamically;

public class InsertionSort {

    public static void main(String[] args) {
        checkTimeDynamically(InsertionSort::sort, 5, 1_000, 101_000, 5_000);
    }

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            final var pickedCard = arr[i];
            var currentPosition = i;

            while (currentPosition > 0 && pickedCard < arr[currentPosition - 1]) {
                arr[currentPosition] = arr[currentPosition - 1];
                currentPosition--;
            }

//            for (int j = i - 1; j >= 0; j--) {
//                if (arr[j] > pickedCard) {
//                    arr[j + 1] = arr[j];
//                    currentPosition = j;
//                }
//            }
            arr[currentPosition] = pickedCard;
        }
    }
}
