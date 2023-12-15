package com.christianhein.aoc2023.day15;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day15.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath)[0].split(",");

        System.out.println("Solution to Day 15 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 15 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        int hashSum = Arrays.stream(input)
                .mapToInt(HolidayHash::hash)
                .sum();
        return String.valueOf(hashSum);
    }

    private static String part2Solution(String[] input) {
        LensBoxes boxes = new LensBoxes();
        Arrays.stream(input).forEachOrdered(boxes::executeInstruction);
        return String.valueOf(boxes.getTotalFocusingPower());
    }
}
