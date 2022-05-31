package com.bobocode.hw3.sort;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    public static void checkTimeDynamically(final Consumer<int[]> fnc, final int attempts, final int start, final int end, final int step) {
        for (int n = start; n <= end; n += step) {
            var totalTime = 0;
            var arr = generateArray(n);

            for (int i = 0; i < attempts; i++) {
                var clone = arr.clone();
                var startTime = System.nanoTime();
                fnc.accept(clone);
                var executionTime = (System.nanoTime() - startTime) / 1_000_000; // ms
                totalTime += executionTime;
            }

            // average time
            System.out.printf("%12d %12d%n", n, totalTime / attempts);
        }
    }

    public static void checkTime(final Consumer<int[]> fnc, final int attempts, final int arraySize) {
        var totalTime = 0;

        for (int i = 0; i < attempts; i++) {
            var arr = generateArray(arraySize);
            var startTime = System.nanoTime();
            fnc.accept(arr);
            var executionTime = (System.nanoTime() - startTime) / 1_000_000; // ms
            System.out.printf("%12d %12dms%n", arraySize, executionTime);
            totalTime += executionTime;
        }

        var averageTime = totalTime / attempts;
        System.out.println("Average:");
        System.out.printf("%12d %12dms%n", arraySize, averageTime);
    }

    public static int[] generateArray(final int n) {
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(n);
        }

        return arr;
    }
}
