package com.christianhein.aoc2023.day08;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day08.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 8 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 8 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        return new Solution().part1(input);
    }

    private static String part2Solution(String[] input) {
        return new Solution().part2Smart(input);
    }
}
