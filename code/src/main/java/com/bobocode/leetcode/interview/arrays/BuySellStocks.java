package com.bobocode.leetcode.interview.arrays;

public class BuySellStocks {

    public static void main(String[] args) {
        var arr = new int[]{7, 1, 5, 3, 6, 4};
        var res = analyze(arr);
        System.out.println(res);
    }

    private static int analyze(int[] prices) {
        var profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }
}
