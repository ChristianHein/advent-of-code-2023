package org.example.day03;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day03.txt";
        String[] input = linesFromResource(inputFilepath);

        Schematic schematic = new Schematic(input);
        List<Integer> partNumbers = schematic.getAllPartNumbers();
        int sumOfPartNumbers = partNumbers.stream().reduce(0, Integer::sum);

        System.out.println("Solution to puzzle (part 1): " + sumOfPartNumbers);

        int sumOfGearRatios = 0;
        for (Point gear : schematic.getGears()) {
            sumOfGearRatios += schematic.getPartNumbers(gear).stream().reduce(1, Math::multiplyExact);
        }

        System.out.println("Solution to puzzle (part 2): " + sumOfGearRatios);
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
