package com.christianhein.aoc2023.day01;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputPathString = "input-day01.txt";
        String[] input = IOUtils.linesFromResource(inputPathString);

        System.out.println("Solution to puzzle: "
                + CalibrationValues.sumOfCalibrationValuesFromAmendedLines(input));
    }
}

