package com.bobocode.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {

    public static void main(String[] args) {
        var empty = groupAnagrams(new String[]{""});
        var result = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        System.out.println(result);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {

        var map = new HashMap<String, List<String>>();

        for (var i = 0; i < strs.length; i++) {
            var str = strs[i];
            var chars = str.toCharArray();
            Arrays.sort(chars);
            var key = String.valueOf(chars);
            map.compute(key, (k, v) -> {
                if (v == null) {
                    var list = new ArrayList<String>();
                    list.add(str);
                    return list;
                } else {
                    v.add(str);
                    return v;
                }
            });
        }

        return map.values().stream()
                .sorted(Comparator.comparingInt(List::size))
                .toList();
    }
}
