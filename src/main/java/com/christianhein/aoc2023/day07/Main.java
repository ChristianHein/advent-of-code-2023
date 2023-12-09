package com.christianhein.aoc2023.day07;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day07.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 7 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 7 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        CamelCardsRound round = new CamelCardsRound(input, false);
        return String.valueOf(round.totalWinnings());
    }

    private static String part2Solution(String[] input) {
        CamelCardsRound round = new CamelCardsRound(input, true);
        return String.valueOf(round.totalWinnings());
    }
}
