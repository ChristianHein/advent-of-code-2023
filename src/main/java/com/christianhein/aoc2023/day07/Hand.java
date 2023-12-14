package com.christianhein.aoc2023.day07;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.*;

public class Hand implements Comparable<Hand> {
    public final List<PlayingCard> cards;
    public final Hand bestHand;
    public final boolean useJokersInsteadOfJacks;

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

    private static final Hand WORST_HAND = new Hand("23456", true);

    public Hand(String input, boolean useJokersInsteadOfJacks) {
        if (input.length() != 5) {
            throw new IllegalArgumentException("Hand must contain exactly 5 playing cards");
        }

        this.useJokersInsteadOfJacks = useJokersInsteadOfJacks;
        this.cards = input.chars()
                .mapToObj(c -> PlayingCard.fromLabel((char) c, useJokersInsteadOfJacks))
                .toList();
        this.bestHand = bestPossibleHandVersion();
        this.cardGroupsSortedLongestFirst = parseGroups();
        this.handType = determineType();
    }

    public Hand bestPossibleHandVersion() {
        if (!this.cards.contains(PlayingCard.Joker)) {
            return this;
        }

        Hand bestHand = WORST_HAND;
        for (PlayingCard card : this.cards) {
            if (card != PlayingCard.Joker) {
                continue;
            }
            for (PlayingCard possibleReplacement : PlayingCard.values()) {
                if (possibleReplacement == PlayingCard.Joker || possibleReplacement == PlayingCard.Jack) {
                    continue;
                }
                Hand newCards = new Hand(this.toString().replaceFirst("J", String.valueOf(possibleReplacement.label)), true)
                        .bestPossibleHandVersion();
                bestHand = bestHand.compareTo(newCards) > 0 ? bestHand : newCards;
            }
        }
        return bestHand;
    }

    public HandType getType() {
        return handType;
    }

    @Override
    public int compareTo(Hand other) {
        // Compare types based on best version of hand
        int compare = this.bestHand.handType.compareTo(other.bestHand.handType);
        if (compare != 0) {
            return compare;
        }

        // Compare hands of same type based on actual hand (not best hand version)
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

    private List<String> parseGroups() {
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
