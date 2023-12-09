package com.christianhein.aoc2023.day07;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.*;

public class Hand implements Comparable<Hand> {
    public final List<PlayingCard> cards;

    public enum HandType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind,
    }

    private final HandType handType;
    private final List<String> cardGroupsSortedLongestFirst;

    public Hand(String input, boolean useJokersInsteadOfJacks) {
        final int CARDS_PER_HAND = 5;
        assert (input.length() == CARDS_PER_HAND);
        this.cards = input.chars()
                .mapToObj(c -> PlayingCard.fromLabel((char) c, useJokersInsteadOfJacks))
                .toList();
        this.cardGroupsSortedLongestFirst = parseGroups(useJokersInsteadOfJacks);
        this.handType = determineType();
    }

    public HandType getType() {
        return handType;
    }

    @Override
    public int compareTo(Hand other) {
        int compare = this.handType.compareTo(other.handType);
        if (compare != 0) {
            return compare;
        }

        for (int cardIndex = 0; cardIndex < 5; cardIndex++) {
            PlayingCard thisCard = this.cards.get(cardIndex);
            PlayingCard otherCard = other.cards.get(cardIndex);
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

    private List<String> parseGroups(boolean useJokersInsteadOfJacks) {
        List<PlayingCard> sortedCards = new ArrayList<>(this.cards);
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
            PlayingCard leftGroupCards = PlayingCard.fromLabel(left.charAt(0), useJokersInsteadOfJacks);
            PlayingCard rightGroupCards = PlayingCard.fromLabel(right.charAt(0), useJokersInsteadOfJacks);
            return leftGroupCards.compareTo(rightGroupCards);
        }).reversed());

        return cardGroupsSortedLongestFirst;
    }

    private HandType determineType() {
        var cardGroups = cardGroupsSortedLongestFirst;
        if (cardGroups.size() == 1 && cardGroups.get(0).length() == 5) {
            return HandType.FiveOfAKind;
        }
        if (cardGroups.size() == 2 && cardGroups.get(0).length() == 4) {
            return HandType.FourOfAKind;
        }
        if (cardGroups.size() == 2 && cardGroups.get(0).length() == 3 && cardGroups.get(1).length() == 2) {
            return HandType.FullHouse;
        }
        if (cardGroups.size() == 3 && cardGroups.get(0).length() == 3 && cardGroups.get(1).length() == 1) {
            return HandType.ThreeOfAKind;
        }
        if (cardGroups.size() == 3 && cardGroups.get(0).length() == 2 && cardGroups.get(1).length() == 2) {
            return HandType.TwoPair;
        }
        if (cardGroups.size() == 4 && cardGroups.get(0).length() == 2 && cardGroups.get(1).length() == 1) {
            return HandType.OnePair;
        }
        return HandType.HighCard;
    }
}
