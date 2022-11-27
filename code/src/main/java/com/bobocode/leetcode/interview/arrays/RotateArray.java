package com.bobocode.leetcode.interview.arrays;

import java.util.Arrays;

public class RotateArray {

    public static void main(String[] args) {
        var arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        reverse(arr, 3);
//        copy(arr, 3);
//        run(arr, 3);
//        test(arr, 3);
//        rotate(arr, 3);
        System.out.println(Arrays.toString(arr));

        var arr2 = new int[]{1, 2};
        reverse(arr2, 3);
//        copy(arr2, 3);
//        run(arr2, 3);
//        test(arr2, 3);
//        rotate(arr2, 3);
        System.out.println(Arrays.toString(arr2));
    }

    private static void reverse(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length);
        reverse(nums, 0, k);
        reverse(nums, k, nums.length);
    }

    private static void reverse(int[] arr, int inclusiveFrom, int exclusiveTo) {
        var margin = (exclusiveTo - inclusiveFrom) / 2;
        for (int i = 0; i < margin; i++) {
            var tmp = arr[i + inclusiveFrom];
            arr[i + inclusiveFrom] = arr[exclusiveTo - i - 1];
            arr[exclusiveTo - i - 1] = tmp;
        }
    }

    private static void copy(int[] nums, int k) {
        k = k % nums.length;
        var tmp = new int[k];
        System.arraycopy(nums, nums.length - k, tmp, 0, k);
        System.arraycopy(nums, 0, nums, k, nums.length - k);
        System.arraycopy(tmp, 0, nums, 0, k);
    }

    private static void run(int[] nums, int k) {
        k = k % nums.length;
        var tmp = new int[k];
        var tmpIndex = 0;
        for (int i = 0; i < k; i++) {
            tmp[tmpIndex] = nums[i + k + 1];
            tmpIndex++;
        }

        for (int i = nums.length - 1; i > k - 1; i--) {
            nums[i] = nums[i - k];
        }

        for (int i = 0; i < k; i++) {
            nums[i] = tmp[i];
        }
    }

    private static int[] test(int[] nums, int k) {
        k = k % nums.length;
        var newArr = new int[nums.length];
        var index = 0;
        var flag = false;
        for (int i = nums.length - k; i <= nums.length; i++) {
            if (flag && i == nums.length - k) {
                break;
            }
            if (!flag && i == nums.length) {
                i = 0;
                flag = true;
            }

            newArr[index] = nums[i];
            index++;
        }

        return newArr;
    }

    private static void rotate(int[] nums, int k) {
        k = k % nums.length;
        for (int i = 0; i < k; i++) {
            var item = nums[nums.length - 1];
            for (int j = nums.length - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = item;
        }
    }
}
