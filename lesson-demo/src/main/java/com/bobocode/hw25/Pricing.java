package com.bobocode.hw25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pricing {

    public static final double BLOCK = 1000;

    public static void main(String[] args) {

        for (int i = 0; i <= 50_000; i += BLOCK) {
            var price = getPrice(i);

//            System.out.println(String.format("%6d %4d", i, price));
            System.out.println(String.format("%6d %2d %4d", i, getBlocks(i), price));
        }
    }


    private static int getPrice(int bidCount) {
        int blocks = getBlocks(bidCount);
        var price = getPriceMap().entrySet().stream()
                .filter(el -> el.getKey().contains(blocks))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(100);

        return 200 * blocks;
    }

    private static int getBlocks(int bidCount) {
        if (bidCount == 0) return 0;
        return bidCount < BLOCK ? 1 : (int) Math.ceil(bidCount / BLOCK);
    }

    private static Map<List<Integer>, Integer> getPriceMap() {
        var map = new HashMap<List<Integer>, Integer>();
        map.put(List.of(0), 0);
        map.put(List.of(1, 2, 3), 175);
        map.put(List.of(4, 5, 6), 165);
        map.put(List.of(7, 8, 9), 155);

        map.put(List.of(10, 11, 12), 150);
        map.put(List.of(13, 14, 15), 145);
        map.put(List.of(16, 17, 18), 140);
        map.put(List.of(19, 20), 135);

        return map;
    }
}
