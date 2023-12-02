package org.example.day01;

import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputPathString = "input-day01.txt";
        String[] input = linesFromResource(inputPathString);

        System.out.println("Solution to puzzle: "
                + CalibrationValues.sumOfCalibrationValuesFromAmendedLines(input));
    }

    private static String[] linesFromResource(String resourceName) throws FileNotFoundException {
        InputStream fileInputStream = Main.class.getClassLoader().getResourceAsStream(resourceName);
        if (fileInputStream == null) {
            throw new FileNotFoundException();
        }
        return new BufferedReader(new InputStreamReader(fileInputStream))
                .lines()
                .toArray(String[]::new);
    }
}

