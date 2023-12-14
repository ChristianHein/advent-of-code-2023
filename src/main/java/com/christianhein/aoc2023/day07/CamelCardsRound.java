package com.christianhein.aoc2023.day07;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CamelCardsRound {
    // Assumption: Only one bid per unique hand.
    public LinkedHashMap<Hand, Integer> handToBidMap = new LinkedHashMap<>();

    public CamelCardsRound(String[] input, boolean useJokersInsteadOfJacks) {
        Arrays.stream(input)
                .map(line -> line.split(" ", 2))
                .forEach(line -> handToBidMap.put(new Hand(line[0], useJokersInsteadOfJacks), Integer.parseInt(line[1])));
    }

    public long totalWinnings() {
        LinkedHashMap<Hand, Integer> sortedHands = handToBidMap.sequencedEntrySet().stream()
                .parallel()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
        SequencedCollection<Integer> bids = sortedHands.sequencedValues();
        return LongStream.range(0, sortedHands.size())
                .map(i -> (i + 1) * bids.removeFirst())
                .sum();
    }
}
