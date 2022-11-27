package com.bobocode.leetcode;

import java.util.Arrays;

public class TwoSum {

    public static void main(String[] args) {
//        var result = twoSum(new int[]{2, 7, 11, 15}, 9);
//        var result = twoSum(new int[]{3, 2, 4}, 6);
        var result = twoSum(new int[]{2,5,5,11}, 10);
        System.out.println(Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {
        Integer tmp = null;
        var index = 0;
        while (true) {
            for (var i = index; i < nums.length; i++) {
                if (tmp == null) {
                    tmp = i;
                } else {
                    if (nums[tmp] + nums[i] == target) {
                        return new int[]{tmp, i};
                    }
                }
            }

            index++;
            tmp = null;
        }
    }

    public static int[] twoSum1(int[] nums, int target) {
        var index = 0;
        int[] result = null;
        while (result == null) {
            index++;
            nums = Arrays.stream(nums).skip(index).toArray();
            result = find(nums, target, index);
        }

        return result;
    }

    private static int[] find(int[] nums, int target, int cycleIndex) {
        Integer first = null;
        for (var i = 0; i < nums.length; i++) {
            if (first == null) {
                first = i;
            } else {
                if (nums[first] + nums[i] == target) {
                    return new int[]{first + cycleIndex, i + cycleIndex};
                }
            }
        }

        return null;
    }
}
