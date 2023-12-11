package com.christianhein.aoc2023.day10;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day10.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 10 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 10 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        PipeMaze maze = new PipeMaze(input);
        int farthestPointDistance = maze.calculateMainPipeLength() / 2;
        return String.valueOf(farthestPointDistance);
    }

    private static String part2Solution(String[] input) {
        PipeMaze maze = new PipeMaze(input);
        int result = maze.tilesEnclosedByMainLoop().size();
        return String.valueOf(result);
    }
}
