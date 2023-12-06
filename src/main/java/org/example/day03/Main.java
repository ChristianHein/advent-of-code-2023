package org.example.day03;

import org.example.util.IOUtils;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day03.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

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
}
