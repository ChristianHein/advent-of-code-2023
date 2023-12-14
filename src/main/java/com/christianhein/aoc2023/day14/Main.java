package com.christianhein.aoc2023.day14;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day14.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        DishPlatform platform = new DishPlatform(input);

        System.out.println("Solution to Day 14 puzzle (part 1): " + part1Solution(platform));
        System.out.println("Solution to Day 14 puzzle (part 2): " + part2Solution(platform));
    }

    private static String part1Solution(DishPlatform platform) {
        platform.tiltPlatform(DishPlatform.Direction.North);
        return String.valueOf(platform.totalLoadOnNorthSupportBeams());
    }

    private static String part2Solution(DishPlatform platform) {
        platform.cyclePlatform(1_000_000_000);
        return String.valueOf(platform.totalLoadOnNorthSupportBeams());
    }
}
