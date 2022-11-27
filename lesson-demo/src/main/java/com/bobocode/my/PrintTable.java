package com.bobocode.my;

public class PrintTable {

    private static final int[] spaces = new int[5];

    public static void main(String[] args) {
        var arr = new String[]{"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
        findMaxSpaces(arr);
        printFormatted(arr);
    }

    private static void printFormatted(String[] arr) {
        for (var i = 0; i < arr.length; i++) {
            if (i % 5 == 0) {
                System.out.println();
            }
            System.out.print(arr[i] + " ".repeat(spaces[i % 5] + 4 - arr[i].length()));
        }
    }

    private static void printFormatted1(String[] arr) {
        for (var i = 0; i < arr.length; i++) {
            var index = i % 5;
            System.out.print(arr[i] + " ".repeat(spaces[index] + 4 - arr[i].length()));
            if (i != 0 && (i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    private static void findMaxSpaces(String[] arr) {
        for (var i = 0; i < arr.length; i++) {
            var index = i % 5;
            if (spaces[index] < arr[i].length()) {
                spaces[index] = arr[i].length();
            }
        }
    }
}
