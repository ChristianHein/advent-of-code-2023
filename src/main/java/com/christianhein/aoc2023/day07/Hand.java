package com.christianhein.aoc2023.day07;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.*;

public class Hand implements Comparable<Hand> {
    public final List<StandardDeckPlayingCard> cards;

    public enum Type {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind,
    }

    private final Type type;
    private final List<String> cardGroupsSortedLongestFirst;

    public Hand(String input) {
        final int CARDS_PER_HAND = 5;
        assert (input.length() == CARDS_PER_HAND);
        this.cards = input.chars()
                .mapToObj(c -> StandardDeckPlayingCard.fromLabel((char) c))
                .toList();
        this.cardGroupsSortedLongestFirst = parseGroups();
        this.type = determineType();
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Hand other) {
        int compare = this.type.compareTo(other.type);
        if (compare != 0) {
            return compare;
        }

        for (int cardIndex = 0; cardIndex < 5; cardIndex++) {
            StandardDeckPlayingCard thisCard = this.cards.get(cardIndex);
            StandardDeckPlayingCard otherCard = other.cards.get(cardIndex);
            compare = thisCard.compareTo(otherCard);
            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(card -> card.label)
                .map(String::valueOf)
                .reduce("", String::concat);
    }

    private List<String> parseGroups() {
        List<StandardDeckPlayingCard> sortedCards = new ArrayList<>(this.cards);
        sortedCards.sort(Comparator.naturalOrder());
        String sortedHand = sortedCards.stream()
                .map(card -> card.label)
                .map(String::valueOf)
                .reduce("", String::concat);

        List<String> cardGroupsSortedLongestFirst = new ArrayList<>(List.of(StringUtils.splitStringIntoRepeatingGroups(sortedHand)));
        cardGroupsSortedLongestFirst.sort(((Comparator<String>) (left, right) -> {
            int compare = Integer.compare(left.length(), right.length());
            if (compare != 0) {
                return compare;
            }
            StandardDeckPlayingCard leftGroupCards = StandardDeckPlayingCard.fromLabel(left.charAt(0));
            StandardDeckPlayingCard rightGroupCards = StandardDeckPlayingCard.fromLabel(right.charAt(0));
            return leftGroupCards.compareTo(rightGroupCards);
        }).reversed());

        return cardGroupsSortedLongestFirst;
    }

    private Type determineType() {
        var cardGroups = cardGroupsSortedLongestFirst;
        if (cardGroups.size() == 1 && cardGroups.get(0).length() == 5) {
            return Type.FiveOfAKind;
        }
        if (cardGroups.size() == 2 && cardGroups.get(0).length() == 4) {
            return Type.FourOfAKind;
        }
        if (cardGroups.size() == 2 && cardGroups.get(0).length() == 3 && cardGroups.get(1).length() == 2) {
            return Type.FullHouse;
        }
        if (cardGroups.size() == 3 && cardGroups.get(0).length() == 3 && cardGroups.get(1).length() == 1) {
            return Type.ThreeOfAKind;
        }
        if (cardGroups.size() == 3 && cardGroups.get(0).length() == 2 && cardGroups.get(1).length() == 2) {
            return Type.TwoPair;
        }
        if (cardGroups.size() == 4 && cardGroups.get(0).length() == 2 && cardGroups.get(1).length() == 1) {
            return Type.OnePair;
        }
        return Type.HighCard;
    }
}
