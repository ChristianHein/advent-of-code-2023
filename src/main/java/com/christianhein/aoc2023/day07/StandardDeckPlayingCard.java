package com.christianhein.aoc2023.day07;

import java.util.Arrays;

public enum StandardDeckPlayingCard implements Comparable<StandardDeckPlayingCard> {
    Two('2'),
    Three('3'),
    Four('4'),
    Five('5'),
    Six('6'),
    Seven('7'),
    Eight('8'),
    Nine('9'),
    Ten('T'),
    Jack('J'),
    Queen('Q'),
    King('K'),
    Ace('A');

    public final char label;

    StandardDeckPlayingCard(char label) {
        this.label = label;
    }

    public static StandardDeckPlayingCard fromLabel(char label) {
        return Arrays.stream(StandardDeckPlayingCard.values())
                .filter(card -> card.label == label)
                .findAny()
                .orElseThrow();
    }
}
