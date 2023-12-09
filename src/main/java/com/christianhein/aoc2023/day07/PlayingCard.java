package com.christianhein.aoc2023.day07;

public enum PlayingCard {
    Joker('J'),
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

    PlayingCard(char label) {
        this.label = label;
    }

    public static PlayingCard fromLabel(char label, boolean useJokersInsteadOfJacks) {
        return switch (label) {
            case 'J' -> useJokersInsteadOfJacks ? Joker : Jack;
            case '2' -> Two;
            case '3' -> Three;
            case '4' -> Four;
            case '5' -> Five;
            case '6' -> Six;
            case '7' -> Seven;
            case '8' -> Eight;
            case '9' -> Nine;
            case 'T' -> Ten;
            case 'Q' -> Queen;
            case 'K' -> King;
            case 'A' -> Ace;
            default -> throw new IllegalArgumentException();
        };
    }
}
