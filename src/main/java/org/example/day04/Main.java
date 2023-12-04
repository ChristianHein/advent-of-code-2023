package org.example.day04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day04.txt";
        String[] input = linesFromResource(inputFilepath);

        int sumOfSingleCardPointValues = 0;
        ArrayList<ScratchCard> scratchCards = new ArrayList<>();
        for (String scratchCardStr : input) {
            ScratchCard card = new ScratchCard(scratchCardStr);
            sumOfSingleCardPointValues += card.pointValue();
            scratchCards.add(card);
        }

        System.out.println("Solution to Day 4 puzzle (part 1): " + sumOfSingleCardPointValues);

        ScratchCardGame game = new ScratchCardGame(scratchCards);
        System.out.println("Solution to Day 4 puzzle (part 2): " + game.totalScratchCardsAtEndOfGame());
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
