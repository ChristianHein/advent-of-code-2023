package com.christianhein.aoc2023.day07;

import java.util.*;
import java.util.stream.LongStream;

public class CamelCardsRound {
    // Assumption: Only one bid per unique hand.
    public LinkedHashMap<Hand, Integer> handsToBids = new LinkedHashMap<>();

    public CamelCardsRound(String[] input, boolean useJokersInsteadOfJacks) {
        Arrays.stream(input)
                .map(line -> line.split(" ", 2))
                .forEach(line -> handsToBids.put(new Hand(line[0], useJokersInsteadOfJacks), Integer.parseInt(line[1])));
    }

    public long totalWinnings() {
        SortedMap<Hand, Integer> sortedHands = new TreeMap<>(handsToBids);
        SequencedCollection<Integer> bids = sortedHands.sequencedValues();
        return LongStream.range(0, sortedHands.size())
                .map(i -> (i + 1) * bids.removeFirst())
                .sum();
    }
}
