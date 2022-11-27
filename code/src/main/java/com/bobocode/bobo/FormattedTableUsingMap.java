package com.bobocode.bobo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormattedTableUsingMap {

    private static final Map<Integer, Integer> spaces = new HashMap<>();

    public static void main(String[] args) {
        var input = new String[]{"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
        findMaxSpaces(input);
        format(input);
    }

    private static void findMaxSpaces(String[] arr) {
        for (var i = 0; i < arr.length; i++) {
            var index = i % 5;
            var currentLength = arr[i].length();
            spaces.compute(index, (key, prevLength) -> Objects.isNull(prevLength) || prevLength < currentLength ? currentLength : prevLength);
        }
    }

    private static void format(String[] input) {
        for (var i = 0; i < input.length; i++) {
            var newRowCondition = i != 0 && (i + 1) % 5 == 0;
            var maxStrLengthInColumn = spaces.get(i % 5);
            var str = input[i];
            if (newRowCondition) str += "\n";
            else str += " ".repeat(maxStrLengthInColumn + 4 - str.length());

            System.out.print(str);
        }
    }
}
