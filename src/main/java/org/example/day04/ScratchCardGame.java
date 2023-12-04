package org.example.day04;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public record ScratchCardGame(List<ScratchCard> scratchCards) {
    // Assumption made: Scratch cards are pre-sorted by ID and start at ID=1.
    public int totalScratchCardsAtEndOfGame() {
        Map<Integer, Integer> cardIdToCount = new HashMap<>();

        BiFunction<Integer, Integer, Integer> incrementCardCount = (cardId, amount) -> {
            return cardIdToCount.compute(cardId, (unusedId, count) -> {
                if (count == null) {
                    return amount;
                }
                return count + amount;
            });
        };

        for (ScratchCard card : scratchCards) {
            incrementCardCount.apply(card.id, 1);
            int copiesOfCurrentCard = cardIdToCount.getOrDefault(card.id, 0);

            int numberOfMatchingNumbers = card.matchingNumbers().size();
            for (int wonCardId = card.id + 1;
                 wonCardId <= card.id + numberOfMatchingNumbers && wonCardId <= scratchCards.size(); wonCardId++) {
                incrementCardCount.apply(wonCardId, copiesOfCurrentCard);
            }
        }
        return cardIdToCount.values().stream().reduce(0, Math::addExact);
    }
}
