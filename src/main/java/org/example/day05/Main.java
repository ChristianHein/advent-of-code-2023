package org.example.day05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day05.txt";
        String[] input = linesFromResource(inputFilepath);

        Almanac almanac1 = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Literal);
        long lowestLocationNumber1 = Long.MAX_VALUE;
        for (LongRange seedRange : almanac1.seeds) {
            assert (seedRange.low() == seedRange.high());
            long seed = seedRange.low();
            long location = almanac1.seedToLocation(seed);
            lowestLocationNumber1 = Math.min(lowestLocationNumber1, location);
        }
        System.out.println("Solution to Day 5 puzzle (part 1): " + lowestLocationNumber1);

        Almanac almanac2 = Almanac.almanacFromInput(input, Almanac.SeedsParsingMode.Ranges);
        ExecutorService exec = Executors.newFixedThreadPool(almanac2.seeds.size());
        List<Future<Long>> futures = new ArrayList<>();
        for (LongRange seedRange : almanac2.seeds) {
            futures.add(exec.submit(() -> StreamSupport.stream(seedRange.spliterator(), false)
                    .map(almanac2::seedToLocation)
                    .min(Long::compare)
                    .orElse(Long.MAX_VALUE)));
        }
        long lowestLocationNumber2 = futures.stream().map(longFuture -> {
            try {
                return longFuture.get();
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).min(Long::compare).orElse(-1L);
        exec.shutdown();

        System.out.println("Solution to Day 5 puzzle (part 2): " + lowestLocationNumber2);
    }

    private static String[] linesFromResource(String resourceName) throws FileNotFoundException {
        InputStream fileInputStream = org.example.day01.Main.class.getClassLoader().getResourceAsStream(resourceName);
        if (fileInputStream == null) {
            throw new FileNotFoundException();
        }
        return new BufferedReader(new InputStreamReader(fileInputStream))
                .lines()
                .toArray(String[]::new);
    }
}
