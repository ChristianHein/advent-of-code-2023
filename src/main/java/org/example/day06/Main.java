package org.example.day06;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day06.txt";
        String[] input = linesFromResource(inputFilepath);

        System.out.println("Solution to Day 6 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 6 puzzle (part 2): " + part2Solution(input));
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

    private static String part1Solution(String[] input) {
        assert (input.length == 2);

        BoatRaceRecordTable table = new BoatRaceRecordTable(input[0], input[1]);
        long productOfWaysToBeatRecords = table.records.stream()
                .map(BoatRaceRecord::numberOfWaysToBeatRecord)
                .reduce(Math::multiplyExact)
                .orElse(0L);
        return Long.toString(productOfWaysToBeatRecords);
    }

    private static String part2Solution(String[] input) {
        assert (input.length == 2);
        String singleTime = input[0].replaceFirst("Time:", "").replaceAll("\\s", "");
        String singleDistance = input[1].replaceFirst("Distance:", "").replaceAll("\\s", "");

        BoatRaceRecordTable table = new BoatRaceRecordTable(singleTime, singleDistance);
        long productOfWaysToBeatRecords = table.records.stream()
                .map(BoatRaceRecord::numberOfWaysToBeatRecord)
                .reduce(Math::multiplyExact)
                .orElse(0L);
        return Long.toString(productOfWaysToBeatRecords);
    }
}
