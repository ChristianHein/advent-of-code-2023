package com.christianhein.aoc2023.day05;

// Inclusive low, exclusive high.
public record LongRange(long low, long high) {
    public boolean contains(long value) {
        return low <= value && value < high;
    }
}
