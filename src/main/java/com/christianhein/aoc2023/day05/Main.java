package com.christianhein.aoc2023.day05;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day05.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 5 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 5 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        Almanac almanac = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Literal);
        long lowestLocationNumber = almanac.seeds.stream()
                .map(seedRange -> {
                    assert seedRange.low() + 1 == seedRange.high();
                    return seedRange.low();
                })
                .min(Long::compare)
                .orElseThrow();
        return Long.toString(lowestLocationNumber);
    }

    private static String part2Solution(String[] input) {
        Almanac almanac = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Ranges);
        long lowestLocationNumber = almanac.seeds.stream()
                .parallel()
                .map(seedRange -> seedRange.stream()
                        .parallel()
                        .map(almanac::seedToLocation)
                        .min()
                        .orElseThrow())
                .min(Long::compare)
                .orElseThrow();
        return Long.toString(lowestLocationNumber);
    }
}
