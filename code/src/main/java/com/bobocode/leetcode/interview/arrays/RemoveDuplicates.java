package com.bobocode.leetcode.interview.arrays;

import java.util.Arrays;

public class RemoveDuplicates {

    public static void main(String[] args) {
        var arr = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        var k = removeDuplicates(arr);
        System.out.println(k);
        System.out.println(Arrays.toString(arr));
    }

    public static int removeDuplicates(int[] nums) {
        var k = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}
