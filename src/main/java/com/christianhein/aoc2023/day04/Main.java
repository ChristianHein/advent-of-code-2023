package com.christianhein.aoc2023.day04;

import com.christianhein.aoc2023.util.IOUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day04.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

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
}
