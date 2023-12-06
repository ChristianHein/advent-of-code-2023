package com.christianhein.aoc2023.day05;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day05.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 5 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 5 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        Almanac almanac = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Literal);
        long lowestLocationNumber = Long.MAX_VALUE;
        for (LongRange seedRange : almanac.seeds) {
            assert (seedRange.low() == seedRange.high());
            long seed = seedRange.low();
            long location = almanac.seedToLocation(seed);
            lowestLocationNumber = Math.min(lowestLocationNumber, location);
        }
        return Long.toString(lowestLocationNumber);
    }

    private static String part2Solution(String[] input) {
        Almanac almanac = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Ranges);
        ExecutorService exec = Executors.newFixedThreadPool(almanac.seeds.size());

        List<Future<Long>> futures = new ArrayList<>();
        for (LongRange seedRange : almanac.seeds) {
            futures.add(exec.submit(() -> seedRange.stream()
                    .map(almanac::seedToLocation)
                    .min()
                    .orElse(Long.MAX_VALUE)));
        }
        long lowestLocationNumber = futures.stream()
                .map(longFuture -> {
                    try {
                        return longFuture.get();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .min(Long::compare)
                .orElse(-1L);
        exec.shutdown();
        return Long.toString(lowestLocationNumber);
    }
}
