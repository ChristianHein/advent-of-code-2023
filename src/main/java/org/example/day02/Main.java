package org.example.day02;

import java.io.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day02.txt";
        String[] input = linesFromResource(inputFilepath);

        HashMap<CubeColor, Integer> bagContents = new HashMap<>();
        bagContents.put(CubeColor.RED, 12);
        bagContents.put(CubeColor.GREEN, 13);
        bagContents.put(CubeColor.BLUE, 14);

        int sumOfPossibleGamesIds = 0;
        int sumOfPowersOfMinimumSetsOfCubes = 0;

        for (String gameString : input) {
            Game game = new Game(gameString);

            if (game.isGamePossible(bagContents)) {
                sumOfPossibleGamesIds += game.getId();
            }
            sumOfPowersOfMinimumSetsOfCubes += game
                    .minimumSetOfCubes()
                    .values()
                    .stream()
                    .reduce(1, Math::multiplyExact);
        }

        System.out.println("Solution to puzzle, part 1: " + sumOfPossibleGamesIds);
        System.out.println("Solution to puzzle, part 2: " + sumOfPowersOfMinimumSetsOfCubes);
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
