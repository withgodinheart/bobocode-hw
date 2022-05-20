package com.bobocode.hw3;

public class FormattedTable {

    public static void main(String[] args) {
        String[] arr = {"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
        printFormatted(arr);
    }

    private static void printFormatted(String[] input) {
        final var maxLengths = new int[5];

        for (int i = 0; i < input.length; i++) {
            if (maxLengths[i % 5] < input[i].length()) {
                maxLengths[i % 5] = input[i].length();
            }
        }

        for (int i = 0; i < input.length; i++) {
            if (i % 5 == 0) {
                System.out.println();
            }

            System.out.print(input[i] + " ".repeat(4 + maxLengths[i % 5] - input[i].length()));
        }
    }
}
