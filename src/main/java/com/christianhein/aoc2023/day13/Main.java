package com.christianhein.aoc2023.day13;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day13.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        Solution solution = new Solution(input);

        System.out.println("Solution to Day 13 puzzle (part 1): " + part1Solution(solution));
        System.out.println("Solution to Day 13 puzzle (part 2): " + part2Solution(solution));
    }

    private static String part1Solution(Solution solution) {
        return String.valueOf(solution.part1());
    }

    private static String part2Solution(Solution solution) {
        return String.valueOf(solution.part2());
    }
}
