package com.christianhein.aoc2023.day12;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Solution {
    public static int part1(String[] input) {
        return Arrays.stream(input)
                .parallel()
                .map(Solution::possibleArrangements)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static int part2(String[] input) {
        // TODO: Implement non-brute-force solution which finishes in a
        //  reasonable amount of time
        return Arrays.stream(input)
                .parallel()
                .map(Solution::unfold)
                .map(Solution::possibleArrangements)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static String unfold(String row) {
        String springConditions = row.split(" ")[0];
        String groups = row.split(" ")[1];

        String unfoldedSpringConditions = String.join("?", springConditions, springConditions, springConditions, springConditions, springConditions);
        String unfoldedGroups = String.join(",", groups, groups, groups, groups, groups);

        return unfoldedSpringConditions + ' ' + unfoldedGroups;
    }

    private static int possibleArrangements(String row) {
        //System.out.println("Entered with row: " + row);
        String springConditions = row.split(" ")[0];
        List<Integer> groups = StringUtils.getPositiveIntsInString(row);
        return _possibleArrangements(springConditions, groups);
    }

    private static int _possibleArrangements(String springConditions, List<Integer> groups) {
        for (int i = 0; i < springConditions.length(); i++) {
            if (springConditions.charAt(i) == '?') {
                String possibility1 = springConditions.substring(0, i) + '.' + springConditions.substring(i + 1);
                String possibility2 = springConditions.substring(0, i) + '#' + springConditions.substring(i + 1);
                return _possibleArrangements(possibility1, groups) + _possibleArrangements(possibility2, groups);
            }
        }
        return isPossibleArrangement(springConditions, groups) ? 1 : 0;
    }

    private static boolean isPossibleArrangement(String springConditions, List<Integer> groups) {
        String[] springs = springConditions.replaceAll("\\.+", " ").trim().split(" ");
        if (springs.length != groups.size()) {
            return false;
        }

        for (int i = 0; i < springs.length; i++) {
            if (springs[i].length() != groups.get(i)) {
                //System.out.println("Not a possible arrangement: " + Arrays.toString(springs) + " " + groups);
                return false;
            }
        }
        //System.out.println("Possible arrangement: " + springConditions + " " + groups);
        return true;
    }
}
