package com.bobocode.my;

public class FormattedTableUsingArray {

    private static final int[] spaces = new int[5];

    public static void main(String[] args) {
        var input = new String[]{"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
        findMaxSpaces(input);
        format(input);
    }

    private static void findMaxSpaces(String[] arr) {
        for (var i = 0; i < arr.length; i++) {
            if (spaces[i % 5] < arr[i].length()) {
                spaces[i % 5] = arr[i].length();
            }
        }
    }

    private static void format(String[] input) {
        for (var i = 0; i < input.length; i++) {
            if (i % 5 == 0) System.out.println();
            System.out.print(input[i] + " ".repeat(spaces[i % 5] + 4 - input[i].length()));
        }
    }
}
