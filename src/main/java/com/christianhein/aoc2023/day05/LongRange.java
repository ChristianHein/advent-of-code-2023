package com.christianhein.aoc2023.day05;

import java.util.stream.LongStream;

// Inclusive low, exclusive high.
public record LongRange(long low, long high) {
    public boolean contains(long value) {
        return low <= value && value < high;
    }

    public LongStream stream() {
        return LongStream.range(low, high);
    }
}
